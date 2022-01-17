package com.sinosoft.cms.base.bean;

import java.io.Serializable;

/**
 * 用户登录对象
 *
 * @author sinosoft
 */
public class LoginBody implements Serializable {
    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    private String validateCode;

    private boolean rememberMe;

    private String uuid;

    /**
     * 用户类型：0-平台注册用户,1-AD域用户,2-单点登录用户
     */
    private Integer type;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getValidateCode() {
        return validateCode;
    }

    public void setValidateCode(String validateCode) {
        this.validateCode = validateCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
