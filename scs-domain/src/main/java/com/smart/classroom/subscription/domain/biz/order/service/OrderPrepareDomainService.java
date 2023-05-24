package com.smart.classroom.subscription.domain.biz.order.service;

import com.smart.classroom.subscription.domain.biz.order.enums.OrderStatus;
import com.smart.classroom.subscription.domain.biz.order.info.PrepareSubscribeInfo;
import com.smart.classroom.subscription.domain.biz.order.model.OrderModel;
import com.smart.classroom.subscription.domain.biz.order.repository.OrderRepository;
import com.smart.classroom.subscription.domain.rpc.column.vo.ColumnVO;
import com.smart.classroom.subscription.domain.rpc.payment.PaymentFacadeClient;
import com.smart.classroom.subscription.domain.rpc.payment.info.PaymentCreateInfo;
import com.smart.classroom.subscription.domain.rpc.payment.info.PreparePaymentInfo;
import com.smart.classroom.subscription.domain.rpc.payment.vo.PaymentVO;
import com.smart.classroom.subscription.domain.rpc.quote.ColumnQuoteFacadeClient;
import com.smart.classroom.subscription.domain.rpc.quote.vo.ColumnQuoteVO;
import com.smart.classroom.subscription.domain.rpc.reader.vo.ReaderVO;
import com.smart.classroom.subscription.utility.exception.UtilException;
import com.smart.classroom.subscription.utility.util.NumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 准备订单 领域服务
 *
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class OrderPrepareDomainService {


    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ColumnQuoteFacadeClient columnQuoteFacadeClient;

    @Autowired
    PaymentFacadeClient paymentFacadeClient;


    /**
     * 订单编号生成规则
     */
    private String generateOrderNo() {
        //订单编号简单使用时间戳
        return "SCO" + System.currentTimeMillis() + NumberUtil.random(1000, 10000);
    }

    /**
     * 创建专栏
     */
    public PrepareSubscribeInfo createAndPrepare(ColumnVO columnVO, ReaderVO readerVO, String payMethod) {

        PreparePaymentInfo preparePaymentInfo = null;
        //2. 查找当前订单是否已经存在了。
        OrderModel orderModel = orderRepository.queryNonFinalState(readerVO, columnVO);
        if (orderModel != null) {

            //根据订单，获取支付的信息。
            preparePaymentInfo = paymentFacadeClient.prepare(orderModel.getPaymentId());

        } else {

            //获取专栏报价。
            ColumnQuoteVO columnQuoteVO = columnQuoteFacadeClient.queryByColumnId(columnVO.getId());
            if (columnQuoteVO == null) {
                throw new UtilException("专栏{}（id={}）尚无报价，无法订阅", columnVO.getName(), columnVO.getId());
            }

            String orderNo = this.generateOrderNo();
            Long price = columnQuoteVO.getPrice();

            PaymentCreateInfo paymentCreateInfo = new PaymentCreateInfo(
                    orderNo,
                    payMethod,
                    price
            );

            //创建对应的支付单。
            preparePaymentInfo = paymentFacadeClient.create(paymentCreateInfo);

            PaymentVO paymentVO = preparePaymentInfo.getPaymentVO();

            //创建订单。
            orderModel = new OrderModel(
                    null,
                    new Date(),
                    new Date(),
                    orderNo,
                    readerVO.getId(),
                    columnVO.getId(),
                    columnQuoteVO.getId(),
                    paymentVO.getId(),
                    price,
                    OrderStatus.CREATED
            );

            //持久化订单。
            orderRepository.insert(orderModel);
        }

        return new PrepareSubscribeInfo(
                orderModel,
                preparePaymentInfo.getThirdTransactionNo(),
                preparePaymentInfo.getNonceStr()
        );

    }

}
