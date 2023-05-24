package com.smart.classroom.subscription.facade.biz.subscription.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lishuang
 * @date 2023-05-12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionPageRequest implements Serializable {
    int pageNum = 1;
    int pageSize = 20;
    //参考 SortDirection枚举
    String orderCreateTime = null;
    //参考 SortDirection枚举
    String orderUpdateTime = null;

    Long readerId = null;
    Long columnId = null;
    Long orderId = null;
    String status = null;
}
