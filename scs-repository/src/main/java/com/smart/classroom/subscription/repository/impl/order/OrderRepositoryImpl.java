package com.smart.classroom.subscription.repository.impl.order;


import com.github.pagehelper.Page;
import com.smart.classroom.subscription.domain.biz.order.enums.OrderStatus;
import com.smart.classroom.subscription.domain.biz.order.model.OrderModel;
import com.smart.classroom.subscription.domain.biz.order.repository.OrderRepository;
import com.smart.classroom.subscription.domain.biz.order.repository.query.OrderPageQuery;
import com.smart.classroom.subscription.domain.rpc.column.vo.ColumnVO;
import com.smart.classroom.subscription.domain.rpc.reader.vo.ReaderVO;
import com.smart.classroom.subscription.repository.impl.order.converter.OrderDO2ModelConverter;
import com.smart.classroom.subscription.repository.impl.order.converter.OrderModel2DOConverter;
import com.smart.classroom.subscription.repository.mapper.order.OrderMapper;
import com.smart.classroom.subscription.repository.orm.order.OrderDO;
import com.smart.classroom.subscription.utility.result.Pager;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Service
public class OrderRepositoryImpl implements OrderRepository {


    @Autowired
    OrderMapper orderMapper;


    @Override
    public OrderModel insert(OrderModel orderModel) {

        OrderDO orderDO = OrderModel2DOConverter.convert(orderModel);

        orderMapper.insert(orderDO);

        //查询一次。
        orderDO = orderMapper.queryById(orderDO.getId());

        return OrderDO2ModelConverter.convert(orderDO);
    }

    @Override
    public OrderModel updatePaymentId(OrderModel orderModel) {




        return null;
    }

    @Override
    public OrderModel queryNonFinalState(ReaderVO readerVO, ColumnVO columnVO) {

        List<OrderStatus> list = Collections.singletonList(OrderStatus.CREATED);
        List<OrderDO> orderDOList = orderMapper.queryByReaderIdAndColumnIdAndStatuses(readerVO.getId(), columnVO.getId(), list);
        if (CollectionUtils.isEmpty(orderDOList)) {
            return null;
        } else {
            return OrderDO2ModelConverter.convert(orderDOList.get(0));
        }
    }

    @Override
    public OrderModel updateStatus(OrderModel orderModel) {

        orderMapper.updateStatus(orderModel.getId(),orderModel.getStatus());
        OrderDO orderDO = orderMapper.queryById(orderModel.getId());

        return OrderDO2ModelConverter.convert(orderDO);
    }

    @Override
    public OrderModel queryById(long orderId) {
        OrderDO orderDO = orderMapper.queryById(orderId);

        return OrderDO2ModelConverter.convert(orderDO);
    }

    @Override
    public OrderModel queryByNo(String orderNo) {

        OrderDO orderDO = orderMapper.queryByNo(orderNo);

        return OrderDO2ModelConverter.convert(orderDO);
    }

    @Override
    public Pager<OrderModel> page(OrderPageQuery orderPageQuery) {
        Page<OrderDO> page = orderMapper.page(
                orderPageQuery.getPageNum(),
                orderPageQuery.getPageSize(),
                orderPageQuery.getOrderCreateTime(),
                orderPageQuery.getOrderUpdateTime(),
                orderPageQuery.getReaderId(),
                orderPageQuery.getColumnId(),
                orderPageQuery.getColumnQuoteId(),
                orderPageQuery.getPaymentId(),
                orderPageQuery.getStatus()
        );

        List<OrderModel> orderModelList = page.stream().map(OrderDO2ModelConverter::convert).collect(Collectors.toList());

        return new Pager<>(page.getPageNum(), page.getPageSize(), page.getTotal(), orderModelList);
    }
}
