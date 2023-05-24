package com.smart.classroom.subscription.facade.biz.subscription;

import com.smart.classroom.subscription.facade.biz.subscription.request.ReaderColumnQueryRequest;
import com.smart.classroom.subscription.facade.biz.subscription.request.SubscriptionPageRequest;
import com.smart.classroom.subscription.facade.biz.subscription.response.SubscriptionDTO;
import com.smart.classroom.subscription.facade.common.resposne.PagerResponse;

/**
 * @author lishuang
 * @date 2023-05-15
 */
public interface SubscriptionReadFacade {


    /**
     * 查找某个读者关于某个专栏的订阅情况
     */
    SubscriptionDTO queryByColumnIdAndReaderId(ReaderColumnQueryRequest readerColumnQueryRequest);


    /**
     * 查找订阅情况分页
     */
    PagerResponse<SubscriptionDTO> page(SubscriptionPageRequest subscriptionPageRequest);


}
