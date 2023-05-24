package com.smart.classroom.subscription.domain.rpc.payment;

import com.smart.classroom.subscription.domain.rpc.payment.info.PaymentCreateInfo;
import com.smart.classroom.subscription.domain.rpc.payment.info.PreparePaymentInfo;
import com.smart.classroom.subscription.domain.rpc.payment.vo.PaymentVO;

/**
 * @author lishuang
 * @date 2023-05-15
 */
public interface PaymentFacadeClient {

    PaymentVO queryById(long paymentId);

    /**
     * 创建支付单
     * 同时返回可用于支付的物流
     */
    PreparePaymentInfo create(PaymentCreateInfo paymentCreateInfo);

    /**
     * 获取一个支付单对应的支付准备物料等信息。
     */
    PreparePaymentInfo prepare(long paymentId);

}
