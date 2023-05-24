package com.smart.classroom.subscription.repository.impl.subscription.converter;

import com.smart.classroom.subscription.domain.biz.subscription.model.SubscriptionModel;
import com.smart.classroom.subscription.repository.orm.subscription.SubscriptionDO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class SubscriptionModel2DOConverter {

    /**
     * 将模型转换成DO
     */
    public static SubscriptionDO convert(SubscriptionModel subscriptionModel) {
        if (subscriptionModel == null) {
            return null;
        }

        SubscriptionDO subscriptionDO = SubscriptionDO.builder()
                .readerId(subscriptionModel.getReaderId())
                .columnId(subscriptionModel.getColumnId())
                .orderId(subscriptionModel.getOrderId())
                .status(subscriptionModel.getStatus())
                .build();

        subscriptionDO.setId(subscriptionModel.getId());
        subscriptionDO.setCreateTime(subscriptionModel.getCreateTime());
        subscriptionDO.setUpdateTime(subscriptionModel.getUpdateTime());

        return subscriptionDO;

    }


}
