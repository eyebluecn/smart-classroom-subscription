package com.smart.classroom.subscription.infrastructure.rpc.payment.converter;


import com.smart.classroom.misc.facade.biz.payment.response.PaymentDTO;
import com.smart.classroom.subscription.domain.rpc.payment.vo.PaymentVO;

/**
 * @author lishuang
 * @date 2023-05-12
 * 转换器
 */
public class PaymentDTO2VOConverter {

    /**
     * 将DO转换成模型
     */
    public static PaymentVO convert(PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return null;
        }

        return PaymentVO.builder()
                .id(paymentDTO.getId())
                .createTime(paymentDTO.getCreateTime())
                .updateTime(paymentDTO.getUpdateTime())

                .orderNo(paymentDTO.getOrderNo())
                .method(paymentDTO.getMethod())
                .thirdTransactionNo(paymentDTO.getThirdTransactionNo())
                .amount(paymentDTO.getAmount())

                .status(paymentDTO.getStatus())

                .build();
    }


}
