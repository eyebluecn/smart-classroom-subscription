package com.smart.classroom.subscription.infrastructure.rpc.quote;

import com.smart.classroom.misc.facade.biz.payment.PaymentReadFacade;
import com.smart.classroom.misc.facade.biz.payment.response.PaymentDTO;
import com.smart.classroom.misc.facade.biz.payment.response.PreparePaymentDTO;
import com.smart.classroom.misc.facade.biz.quote.ColumnQuoteReadFacade;
import com.smart.classroom.misc.facade.biz.quote.response.ColumnQuoteDTO;
import com.smart.classroom.subscription.domain.rpc.payment.PaymentFacadeClient;
import com.smart.classroom.subscription.domain.rpc.payment.info.PreparePaymentInfo;
import com.smart.classroom.subscription.domain.rpc.payment.vo.PaymentVO;
import com.smart.classroom.subscription.domain.rpc.quote.ColumnQuoteFacadeClient;
import com.smart.classroom.subscription.domain.rpc.quote.vo.ColumnQuoteVO;
import com.smart.classroom.subscription.infrastructure.rpc.payment.converter.PaymentDTO2VOConverter;
import com.smart.classroom.subscription.infrastructure.rpc.payment.converter.PreparePaymentDTO2InfoConverter;
import com.smart.classroom.subscription.infrastructure.rpc.quote.converter.ColumnQuoteDTO2VOConverter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-15
 */
@Service
public class ColumnQuoteFacadeClientImpl implements ColumnQuoteFacadeClient {

    @DubboReference
    ColumnQuoteReadFacade columnQuoteReadFacade;

    @Override
    public ColumnQuoteVO queryById(long columnQuoteId) {

        ColumnQuoteDTO columnQuoteDTO = columnQuoteReadFacade.queryById(columnQuoteId);

        return ColumnQuoteDTO2VOConverter.convert(columnQuoteDTO);
    }

    @Override
    public ColumnQuoteVO queryByColumnId(long columnId) {

        ColumnQuoteDTO columnQuoteDTO = columnQuoteReadFacade.queryByColumnId(columnId);

        return ColumnQuoteDTO2VOConverter.convert(columnQuoteDTO);
    }
}
