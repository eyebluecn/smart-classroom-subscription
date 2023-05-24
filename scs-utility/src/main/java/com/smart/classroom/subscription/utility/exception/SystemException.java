package com.smart.classroom.subscription.utility.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统异常
 * <p>
 * 应用中所有的非业务异常——也就是系统异常，都抛这个异常
 *
 * @author lishuang
 * @date 2023/2/16
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemException extends UtilException {

    public SystemException() {
        super(ExceptionCode.SYSTEM_ERROR);
    }

    public SystemException(String messagePattern, Object... arguments) {
        super(messagePattern, arguments);
    }

    public SystemException(ExceptionCode resultCode) {
        super(resultCode);
    }

    public SystemException(Throwable throwable) {
        super(throwable);
    }

    public SystemException(ExceptionCode resultCode, String messagePattern, Object... arguments) {
        super(resultCode, messagePattern, arguments);
    }
}
