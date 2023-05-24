package com.smart.classroom.subscription.repository.impl.subscription.converter;


import com.smart.classroom.subscription.domain.biz.subscription.model.SubscriptionModel;
import com.smart.classroom.subscription.repository.orm.subscription.SubscriptionDO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class SubscriptionDO2ModelConverter {

    /**
     * 将DO转换成模型
     */
    public static SubscriptionModel convert(SubscriptionDO subscriptionDO) {
        if (subscriptionDO == null) {
            return null;
        }

        return SubscriptionModel.builder()
                .id(subscriptionDO.getId())
                .createTime(subscriptionDO.getCreateTime())
                .updateTime(subscriptionDO.getUpdateTime())
                .readerId(subscriptionDO.getReaderId())
                .columnId(subscriptionDO.getColumnId())
                .orderId(subscriptionDO.getOrderId())
                .status(subscriptionDO.getStatus())
                .build();
    }


}
