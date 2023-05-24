package com.smart.classroom.subscription.domain.biz.subscription.service;

import com.smart.classroom.subscription.domain.biz.order.enums.OrderStatus;
import com.smart.classroom.subscription.domain.biz.order.model.OrderModel;
import com.smart.classroom.subscription.domain.biz.order.repository.OrderRepository;
import com.smart.classroom.subscription.domain.biz.subscription.enums.SubscriptionStatus;
import com.smart.classroom.subscription.domain.biz.subscription.event.SubscriptionDomainEvent;
import com.smart.classroom.subscription.domain.biz.subscription.info.SubscriptionEventPayload;
import com.smart.classroom.subscription.domain.biz.subscription.model.SubscriptionModel;
import com.smart.classroom.subscription.domain.biz.subscription.repository.SubscriptionRepository;
import com.smart.classroom.subscription.domain.middleware.mq.MqProducer;
import com.smart.classroom.subscription.domain.middleware.mq.info.MqSendResultInfo;
import com.smart.classroom.subscription.domain.rpc.column.ColumnFacadeClient;
import com.smart.classroom.subscription.domain.rpc.column.vo.ColumnVO;
import com.smart.classroom.subscription.domain.rpc.payment.vo.PaymentVO;
import com.smart.classroom.subscription.domain.rpc.reader.ReaderFacadeClient;
import com.smart.classroom.subscription.domain.rpc.reader.vo.ReaderVO;
import com.smart.classroom.subscription.utility.exception.UtilException;
import com.smart.classroom.subscription.utility.util.JsonUtil;
import com.smart.classroom.subscription.utility.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 创建专栏领域服务
 *
 * @author lishuang
 * @date 2023-05-12
 */
@Slf4j
@Service
public class SubscriptionCreateDomainService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ReaderFacadeClient readerFacadeClient;

    @Autowired
    ColumnFacadeClient columnFacadeClient;

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    MqProducer mqProducer;


    /**
     * 创建专栏
     */
    public SubscriptionModel create(OrderModel orderModel) {

        //创建一个新的订阅关系。
        SubscriptionModel subscriptionModel = new SubscriptionModel(
                null,
                new Date(),
                new Date(),
                orderModel.getReaderId(),
                orderModel.getColumnId(),
                orderModel.getId(),
                SubscriptionStatus.CREATED
        );

        //持久化 数据库做了唯一联合索引。
        subscriptionModel = subscriptionRepository.insert(subscriptionModel);

        log.info("专栏订阅成功，readerId={} columnId={}", subscriptionModel.getReaderId(), subscriptionModel.getColumnId());

        //发送专栏创建成功的领域事件。
        String uuid = StringUtil.uuid();
        SubscriptionEventPayload subscriptionEventPayload = new SubscriptionEventPayload(
                subscriptionModel.getReaderId(),
                subscriptionModel.getColumnId(),
                subscriptionModel.getOrderId(),
                subscriptionModel.getStatus().name(),
                subscriptionModel.getCreateTime()
        );
        String content = JsonUtil.toJson(subscriptionEventPayload);
        MqSendResultInfo mqSendResultInfo = mqProducer.send(SubscriptionDomainEvent.SUBSCRIPTION_DOMAIN_EVENT_CREATED.name(), uuid, content);
        if (mqSendResultInfo.isSuccess()) {
            log.info("领域事件{}发布成功", SubscriptionDomainEvent.SUBSCRIPTION_DOMAIN_EVENT_CREATED);
        } else {
            log.error("领域事件{}发布失败", SubscriptionDomainEvent.SUBSCRIPTION_DOMAIN_EVENT_CREATED);
        }

        return subscriptionModel;
    }

}
