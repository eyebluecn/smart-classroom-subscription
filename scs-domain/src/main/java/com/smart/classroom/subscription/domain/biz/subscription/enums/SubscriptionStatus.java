package com.smart.classroom.subscription.domain.biz.subscription.enums;

import lombok.Getter;

public enum SubscriptionStatus {

    CREATED("已创建"),
    OK("已生效"),
    DISABLED("已失效");

    @Getter
    private final String description;

    SubscriptionStatus(String description) {
        this.description = description;
    }


    public static String toString(SubscriptionStatus subscriptionStatus) {
        if (subscriptionStatus == null) {
            return null;
        }
        return subscriptionStatus.name();
    }

    public static SubscriptionStatus toEnum(String s) {
        if (s == null) {
            return null;
        }
        return SubscriptionStatus.valueOf(s);
    }

}
