package com.smart.classroom.subscription.facade.biz.subscription.request;

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
public class PrepareSubscribeRequest implements Serializable {

    /**
     * 当前读者id
     */
    private long readerId;

    /**
     * 希望订阅的专栏
     */
    private long columnId;

    /**
     * 支付方式 支持 ALIPAY/WEIXIN
     */
    private String payMethod;


}
