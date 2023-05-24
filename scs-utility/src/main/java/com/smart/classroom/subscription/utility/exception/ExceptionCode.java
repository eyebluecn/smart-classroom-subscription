package com.smart.classroom.subscription.utility.exception;

import lombok.Getter;


/**
 * 所有定义的错误码
 */
public enum ExceptionCode {

    OK("成功"),
    UNAUTHORIZED("没有权限"),
    SYSTEM_ERROR("系统异常"),
    UNKNOWN("服务器未知错误"),
    ;

    @Getter
    private String message;

    ExceptionCode(String message) {
        this.message = message;
    }

}
