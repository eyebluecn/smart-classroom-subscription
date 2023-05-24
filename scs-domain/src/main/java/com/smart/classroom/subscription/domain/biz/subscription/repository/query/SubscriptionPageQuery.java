package com.smart.classroom.subscription.domain.biz.subscription.repository.query;

import com.smart.classroom.subscription.domain.biz.subscription.enums.SubscriptionStatus;
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
public class SubscriptionPageQuery {

    int pageNum = 1;
    int pageSize = 20;

    SortDirection orderCreateTime;
    SortDirection orderUpdateTime;
    Long readerId;
    Long columnId;
    Long orderId;
    SubscriptionStatus status;

}
