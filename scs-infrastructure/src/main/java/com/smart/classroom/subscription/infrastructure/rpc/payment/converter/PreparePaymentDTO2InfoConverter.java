package com.smart.classroom.subscription.infrastructure.rpc.payment.converter;


import com.smart.classroom.misc.facade.biz.payment.response.PreparePaymentDTO;
import com.smart.classroom.subscription.domain.rpc.payment.info.PreparePaymentInfo;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class PreparePaymentDTO2InfoConverter {

    /**
     * 将DO转换成模型
     */
    public static PreparePaymentInfo convert(PreparePaymentDTO preparePaymentDTO) {
        if (preparePaymentDTO == null) {
            return null;
        }

        return PreparePaymentInfo.builder()
                .paymentVO(PaymentDTO2VOConverter.convert(preparePaymentDTO.getPaymentDTO()))
                .thirdTransactionNo(preparePaymentDTO.getThirdTransactionNo())
                .nonceStr(preparePaymentDTO.getNonceStr())
                .build();
    }


}
