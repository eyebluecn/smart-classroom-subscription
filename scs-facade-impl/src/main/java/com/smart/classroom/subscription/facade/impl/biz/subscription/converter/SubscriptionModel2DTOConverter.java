package com.smart.classroom.subscription.facade.impl.biz.subscription.converter;

import com.smart.classroom.subscription.domain.biz.subscription.enums.SubscriptionStatus;
import com.smart.classroom.subscription.domain.biz.subscription.model.SubscriptionModel;
import com.smart.classroom.subscription.facade.biz.subscription.response.SubscriptionDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class SubscriptionModel2DTOConverter {

    /**
     * 将DO转换成模型
     */
    public static SubscriptionDTO convert(SubscriptionModel subscriptionModel) {
        if (subscriptionModel == null) {
            return null;
        }

        SubscriptionDTO subscriptionDTO = SubscriptionDTO.builder()
                .id(subscriptionModel.getId())
                .createTime(subscriptionModel.getCreateTime())
                .updateTime(subscriptionModel.getUpdateTime())

                .readerId(subscriptionModel.getReaderId())
                .columnId(subscriptionModel.getColumnId())
                .orderId(subscriptionModel.getOrderId())
                .status(SubscriptionStatus.toString(subscriptionModel.getStatus()))
                .build();

        return subscriptionDTO;
    }


}
