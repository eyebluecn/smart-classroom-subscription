package com.smart.classroom.subscription.facade.impl.biz.order;

import com.smart.classroom.subscription.domain.biz.order.model.OrderModel;
import com.smart.classroom.subscription.domain.biz.order.repository.OrderRepository;
import com.smart.classroom.subscription.facade.biz.order.OrderReadFacade;
import com.smart.classroom.subscription.facade.biz.order.response.OrderDTO;
import com.smart.classroom.subscription.facade.impl.biz.order.converter.OrderModel2DTOConverter;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

/**
 * @author lishuang
 * @date 2023-05-19
 */
@DubboService
public class OrderReadFacadeImpl implements OrderReadFacade {

    @Resource
    OrderRepository orderRepository;

    @Override
    public OrderDTO queryById(Long orderId) {
        OrderModel orderModel = orderRepository.queryById(orderId);
        return OrderModel2DTOConverter.convert(orderModel);
    }
}
