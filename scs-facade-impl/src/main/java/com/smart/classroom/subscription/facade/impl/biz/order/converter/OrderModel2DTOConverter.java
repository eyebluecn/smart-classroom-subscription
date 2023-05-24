package com.smart.classroom.subscription.facade.impl.biz.order.converter;

import com.smart.classroom.subscription.domain.biz.order.enums.OrderStatus;
import com.smart.classroom.subscription.domain.biz.order.model.OrderModel;
import com.smart.classroom.subscription.facade.biz.order.response.OrderDTO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class OrderModel2DTOConverter {

    /**
     * 将DO转换成模型
     */
    public static OrderDTO convert(OrderModel orderModel) {
        if (orderModel == null) {
            return null;
        }

        OrderDTO orderDTO = OrderDTO.builder()
                .id(orderModel.getId())
                .createTime(orderModel.getCreateTime())
                .updateTime(orderModel.getUpdateTime())
                .no(orderModel.getNo())
                .readerId(orderModel.getReaderId())
                .columnId(orderModel.getColumnId())
                .columnQuoteId(orderModel.getColumnQuoteId())
                .paymentId(orderModel.getPaymentId())
                .price(orderModel.getPrice())
                .status(OrderStatus.toString(orderModel.getStatus()))
                .build();

        return orderDTO;
    }


}
