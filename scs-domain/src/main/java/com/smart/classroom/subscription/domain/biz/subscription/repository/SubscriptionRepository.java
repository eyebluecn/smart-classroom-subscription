package com.smart.classroom.subscription.domain.biz.subscription.repository;

import com.smart.classroom.subscription.domain.biz.subscription.model.SubscriptionModel;
import com.smart.classroom.subscription.domain.biz.subscription.repository.query.SubscriptionPageQuery;
import com.smart.classroom.subscription.domain.rpc.column.vo.ColumnVO;
import com.smart.classroom.subscription.domain.rpc.reader.vo.ReaderVO;
import com.smart.classroom.subscription.utility.result.Pager;

/**
 * @author lishuang
 * @date 2023-05-12
 */
public interface SubscriptionRepository {

    SubscriptionModel queryByReaderAndColumn(ReaderVO readerVO, ColumnVO columnVO);

    //分页查询
    Pager<SubscriptionModel> page(SubscriptionPageQuery subscriptionPageQuery);

    SubscriptionModel insert(SubscriptionModel order);

}
