package com.smart.classroom.subscription.domain.biz.order.info;

import com.smart.classroom.subscription.domain.biz.order.model.OrderModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lishuang
 * @date 2023-05-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrepareSubscribeInfo {

    /**
     * 相关的订单。
     */
    OrderModel orderModel;

    /**
     * 支付的一些token及信息
     */
    String thirdTransactionNo;

    /**
     * 支付时候的验证信息等。
     */
    String nonceStr;

}
