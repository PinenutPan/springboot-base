package com.sinosoft.cms.base.exception;

/**
 * 验证码错误异常类
 *
 * @author sinosoft
 */
public class CaptchaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public CaptchaException(String msg) {
        super(msg);
    }
}
