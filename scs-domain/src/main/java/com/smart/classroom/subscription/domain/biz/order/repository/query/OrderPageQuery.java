package com.smart.classroom.subscription.domain.biz.order.repository.query;

import com.smart.classroom.subscription.domain.biz.order.enums.OrderStatus;
import com.smart.classroom.subscription.utility.enums.SortDirection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lishuang
 * @date 2023-05-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPageQuery {

    int pageNum = 1;
    int pageSize = 20;
    SortDirection orderCreateTime;
    SortDirection orderUpdateTime;
    Long readerId = null;
    Long columnId = null;
    Long columnQuoteId = null;
    Long paymentId = null;
    OrderStatus status = null;

}
