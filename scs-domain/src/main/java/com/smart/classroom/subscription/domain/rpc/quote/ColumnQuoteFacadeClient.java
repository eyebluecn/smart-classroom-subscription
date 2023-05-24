package com.smart.classroom.subscription.domain.rpc.quote;

import com.smart.classroom.subscription.domain.rpc.quote.vo.ColumnQuoteVO;

/**
 * @author lishuang
 * @date 2023-05-15
 */
public interface ColumnQuoteFacadeClient {

    ColumnQuoteVO queryById(long columnQuoteId);


    ColumnQuoteVO queryByColumnId(long columnId);


}
