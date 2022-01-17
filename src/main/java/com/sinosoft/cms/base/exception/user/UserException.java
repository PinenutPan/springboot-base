package com.sinosoft.cms.base.exception.user;


import com.sinosoft.cms.base.exception.BaseException;

/**
 * 用户信息异常类
 *
 * @author sinosoft
 */
public class UserException extends BaseException {
    private static final long serialVersionUID = 1L;

    public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }
}
