package com.smart.classroom.subscription.infrastructure.rpc.payment;

import com.smart.classroom.misc.facade.biz.payment.PaymentReadFacade;
import com.smart.classroom.misc.facade.biz.payment.PaymentWriteFacade;
import com.smart.classroom.misc.facade.biz.payment.request.PaymentCreateRequest;
import com.smart.classroom.misc.facade.biz.payment.response.PaymentDTO;
import com.smart.classroom.misc.facade.biz.payment.response.PreparePaymentDTO;
import com.smart.classroom.subscription.domain.rpc.payment.PaymentFacadeClient;
import com.smart.classroom.subscription.domain.rpc.payment.info.PaymentCreateInfo;
import com.smart.classroom.subscription.domain.rpc.payment.info.PreparePaymentInfo;
import com.smart.classroom.subscription.domain.rpc.payment.vo.PaymentVO;
import com.smart.classroom.subscription.infrastructure.rpc.payment.converter.PaymentDTO2VOConverter;
import com.smart.classroom.subscription.infrastructure.rpc.payment.converter.PreparePaymentDTO2InfoConverter;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.stereotype.Service;

/**
 * @author lishuang
 * @date 2023-05-15
 */
@Service
public class PaymentFacadeClientImpl implements PaymentFacadeClient {

    @DubboReference
    PaymentReadFacade paymentReadFacade;

    @DubboReference
    PaymentWriteFacade paymentWriteFacade;

    @Override
    public PaymentVO queryById(long paymentId) {

        PaymentDTO paymentDTO = paymentReadFacade.queryById(paymentId);

        return PaymentDTO2VOConverter.convert(paymentDTO);
    }

    @Override
    public PreparePaymentInfo create(PaymentCreateInfo paymentCreateInfo) {
        PaymentCreateRequest paymentCreateRequest =
                PaymentCreateRequest.builder()
                        .orderNo(paymentCreateInfo.getOrderNo())
                        .amount(paymentCreateInfo.getAmount())
                        .method(paymentCreateInfo.getMethod())
                        .build();
        PreparePaymentDTO preparePaymentDTO = paymentWriteFacade.create(paymentCreateRequest);

        return PreparePaymentDTO2InfoConverter.convert(preparePaymentDTO);
    }

    @Override
    public PreparePaymentInfo prepare(long paymentId) {
        PreparePaymentDTO preparePaymentDTO = paymentReadFacade.prepare(paymentId);

        return PreparePaymentDTO2InfoConverter.convert(preparePaymentDTO);
    }
}
