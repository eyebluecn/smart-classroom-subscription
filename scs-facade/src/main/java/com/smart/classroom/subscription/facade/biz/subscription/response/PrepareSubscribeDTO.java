package com.smart.classroom.subscription.facade.biz.subscription.response;

import com.smart.classroom.subscription.facade.biz.order.response.OrderDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lishuang
 * @date 2023-05-15
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrepareSubscribeDTO implements Serializable {

    /**
     * 相关的订单。
     */
    OrderDTO orderDTO;

    /**
     * 支付的一些token及信息
     */
    String thirdTransactionNo;

    /**
     * 支付时候的验证信息等。
     */
    String nonceStr;



}
