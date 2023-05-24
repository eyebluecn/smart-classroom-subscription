package com.smart.classroom.subscription.application.biz.subscription;

import com.smart.classroom.subscription.application.rpc.payment.event.PaymentDomainEvent;
import com.smart.classroom.subscription.application.rpc.payment.info.PaymentEventPayload;
import com.smart.classroom.subscription.domain.biz.order.info.PrepareSubscribeInfo;
import com.smart.classroom.subscription.domain.biz.order.model.OrderModel;
import com.smart.classroom.subscription.domain.biz.order.repository.OrderRepository;
import com.smart.classroom.subscription.domain.biz.order.service.OrderPaidDomainService;
import com.smart.classroom.subscription.domain.biz.order.service.OrderPrepareDomainService;
import com.smart.classroom.subscription.domain.biz.subscription.model.SubscriptionModel;
import com.smart.classroom.subscription.domain.biz.subscription.repository.SubscriptionRepository;
import com.smart.classroom.subscription.domain.biz.subscription.service.SubscriptionCreateDomainService;
import com.smart.classroom.subscription.domain.middleware.mq.MqConsumer;
import com.smart.classroom.subscription.domain.middleware.mq.info.MqMessagePayloadInfo;
import com.smart.classroom.subscription.domain.rpc.column.ColumnFacadeClient;
import com.smart.classroom.subscription.domain.rpc.column.vo.ColumnVO;
import com.smart.classroom.subscription.domain.rpc.payment.PaymentFacadeClient;
import com.smart.classroom.subscription.domain.rpc.payment.vo.PaymentVO;
import com.smart.classroom.subscription.domain.rpc.reader.ReaderFacadeClient;
import com.smart.classroom.subscription.domain.rpc.reader.vo.ReaderVO;
import com.smart.classroom.subscription.repository.transaction.TransactionService;
import com.smart.classroom.subscription.utility.exception.UtilException;
import com.smart.classroom.subscription.utility.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

/**
 * @author lishuang
 * @date 2023-05-15
 */
@Slf4j
@Service
public class SubscriptionCommandAppService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    ReaderFacadeClient readerFacadeClient;

    @Autowired
    ColumnFacadeClient columnFacadeClient;

    @Autowired
    PaymentFacadeClient paymentFacadeClient;

    @Autowired
    OrderPrepareDomainService orderPrepareDomainService;

    @Autowired
    OrderPaidDomainService orderPaidDomainService;

    @Autowired
    SubscriptionCreateDomainService subscriptionCreateDomainService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    TransactionService transactionService;

    @Autowired
    MqConsumer mqConsumer;

    /**
     * 注册领域事件监听。
     */
    @EventListener(ContextRefreshedEvent.class)
    public void applicationRefreshed() {

        log.info("注册监听领域事件：{}", PaymentDomainEvent.PAYMENT_DOMAIN_EVENT_PAID);

        mqConsumer.listen(PaymentDomainEvent.PAYMENT_DOMAIN_EVENT_PAID.name(), this::onPaymentPaid);

    }


    /**
     * 发起订阅请求
     */
    public PrepareSubscribeInfo prepareSubscribe(long readerId, long columnId, String payMethod) {

        //1. 查询读者信息。
        ReaderVO readerVO = readerFacadeClient.queryById(readerId);
        if (readerVO == null) {
            throw new UtilException("id={}对应的读者不存在", readerId);
        }


        //2. 查询专栏信息。
        ColumnVO columnVO = columnFacadeClient.queryById(columnId);
        if (columnVO == null) {
            throw new UtilException("id={}对应的专栏不存在", columnId);
        }

        //3. 查找订阅关系是否已经创建了。
        SubscriptionModel subscriptionModel = subscriptionRepository.queryByReaderAndColumn(readerVO, columnVO);
        if (subscriptionModel != null) {
            throw new UtilException("读者{}已经订阅了专栏{}", readerVO.getUsername(), columnVO.getName());
        }


        //4. 要到一个准备单
        PrepareSubscribeInfo prepareSubscribeInfo = orderPrepareDomainService.createAndPrepare(columnVO, readerVO, payMethod);
        //OrderModel orderModel = prepareSubscribeDomainService.prepare(readerVO, columnVO,payMethod);

        return prepareSubscribeInfo;
    }


    /**
     * 支付成功事件
     */
    private boolean onPaymentPaid(MqMessagePayloadInfo mqMessagePayloadInfo) {
        log.info("收到了支付单完成支付的消息:{}", mqMessagePayloadInfo);

        String content = mqMessagePayloadInfo.getContent();
        PaymentEventPayload paymentEventPayload = JsonUtil.toObject(content, PaymentEventPayload.class);
        Long paymentId = paymentEventPayload.getPaymentId();

        this.paymentPaid(paymentId);

        //表示消费成功。
        return true;
    }


    /**
     * 支付已经完成了。
     */
    public SubscriptionModel paymentPaid(long paymentId) {

        log.info("收到支付完成的信息了，准备去确认订单和创建订阅关系。 paymentId={}", paymentId);

        PaymentVO paymentVO = paymentFacadeClient.queryById(paymentId);
        if (paymentVO == null) {
            throw new UtilException("支付单id={}不存在", paymentId);
        }
        OrderModel orderModel = orderRepository.queryByNo(paymentVO.getOrderNo());
        if (orderModel == null) {
            throw new UtilException("支付单id={} no={}没有对应的订单", paymentVO.getId(), paymentVO.getOrderNo());
        }
        ReaderVO readerVO = readerFacadeClient.queryById(orderModel.getReaderId());
        ColumnVO columnVO = columnFacadeClient.queryById(orderModel.getColumnId());
        SubscriptionModel subscriptionModel = subscriptionRepository.queryByReaderAndColumn(readerVO, columnVO);
        if (subscriptionModel != null) {
            log.warn("订阅已经完成了确认，重复的消息投递！");
            return subscriptionModel;
        }


        //手动开启事务
        TransactionStatus transactionStatus = transactionService.begin();
        try {

            orderModel = orderPaidDomainService.orderPaid(orderModel);
            subscriptionModel = subscriptionCreateDomainService.create(orderModel);

            transactionService.commit(transactionStatus);
            log.error("创建订阅关系的事务提交成功");
        } catch (Throwable throwable) {
            log.error("事务提交失败", throwable);
            transactionService.rollback(transactionStatus);
        }

        return subscriptionModel;
    }

}
