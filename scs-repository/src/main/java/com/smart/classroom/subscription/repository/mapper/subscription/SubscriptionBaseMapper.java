/**
 * smart-classroom.com Inc. Copyright (c) 2015-present All Rights Reserved.
 * generated by SaberGenerator
 */
package com.smart.classroom.subscription.repository.mapper.subscription;

import com.github.pagehelper.Page;
import com.smart.classroom.subscription.domain.biz.subscription.enums.SubscriptionStatus;
import com.smart.classroom.subscription.repository.mapper.base.BaseMapper;
import com.smart.classroom.subscription.repository.orm.subscription.SubscriptionDO;
import com.smart.classroom.subscription.utility.enums.SortDirection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SubscriptionBaseMapper extends BaseMapper<SubscriptionDO> {

    /**
     * 当入参中含有PageNum和pageSize时，会自动分页。
     */
    Page<SubscriptionDO> page(
            @Param("pageNum") int pageNum,
            @Param("pageSize") int pageSize,
            @Param("orderCreateTime") SortDirection orderCreateTime,
            @Param("orderUpdateTime") SortDirection orderUpdateTime,
            @Param("readerId") Long readerId,
            @Param("columnId") Long columnId,
            @Param("orderId") Long orderId,
            @Param("status") SubscriptionStatus status
    );

}