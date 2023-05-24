package com.smart.classroom.subscription.facade.biz.order;

import com.smart.classroom.subscription.facade.biz.order.response.OrderDTO;
import com.smart.classroom.subscription.facade.biz.subscription.request.ReaderColumnQueryRequest;
import com.smart.classroom.subscription.facade.biz.subscription.request.SubscriptionPageRequest;
import com.smart.classroom.subscription.facade.biz.subscription.response.SubscriptionDTO;
import com.smart.classroom.subscription.facade.common.resposne.PagerResponse;

/**
 * @author lishuang
 * @date 2023-05-15
 */
public interface OrderReadFacade {

    /**
     * 按照id查询
     */
    OrderDTO queryById(Long orderId);

}
