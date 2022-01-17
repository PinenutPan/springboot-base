package com.sinosoft.cms.base.exception;

/**
 * 登录判断token异常
 *
 * @author sinosoft
 */
public class LoginAuthorizeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public LoginAuthorizeException() {
    }

    public LoginAuthorizeException(String message) {
        super(message);
    }
}
