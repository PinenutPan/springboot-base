package com.sinosoft.cms.base.config;

import com.sinosoft.cms.base.bean.LoginUser;
import com.sinosoft.cms.base.constant.CacheConstants;
import com.sinosoft.cms.base.constant.Constants;
import com.sinosoft.cms.base.constant.PreAuthorizeConstant;
import com.sinosoft.cms.base.exception.LoginAuthorizeException;
import com.sinosoft.cms.base.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author sinosoft
 */
@Component
public class TokenService {
    @Autowired
    private RedisService redisService;

    public final static long EXPIRE_TIME = Constants.TOKEN_EXPIRE * 60;

    public final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    public static final long MILLIS_SECOND = 1000;

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUser loginUser) {
        // 生成token
        String token = IdUtils.fastUUID();
        loginUser.setToken(token);
        loginUser.setUserid(loginUser.getSysUser().getUserId());
        loginUser.setUsername(loginUser.getSysUser().getUserName());
        loginUser.setIpaddr(IpUtils.getIpAddr(ServletUtils.getRequest()));
        refreshToken(loginUser);

        // 保存或更新用户token
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", token);
        map.put("expires_in", EXPIRE_TIME);
        map.put("changeStatus", loginUser.getSysUser().getChangeStatus());
        redisService.setCacheObject(ACCESS_TOKEN + token, loginUser, EXPIRE_TIME, TimeUnit.SECONDS);
        return map;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser() {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUser getLoginUser(HttpServletRequest request) {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            LoginUser user = redisService.getCacheObject(userKey);
            if (Objects.nonNull(user)){
                //添加默认登录权限
                if (null == user.getRoles()) {
                    Set<String> roles = new HashSet<>();
                    user.setRoles(roles);
                }
                if (null == user.getPermissions()) {
                    Set<String> permissions = new HashSet<>();
                    user.setPermissions(permissions);
                }
                user.getRoles().add(PreAuthorizeConstant.LOGIN_DEFAULT_PESS);
                user.getPermissions().add(PreAuthorizeConstant.LOGIN_DEFAULT_PESS);
                redisService.expire(getTokenKey(token), EXPIRE_TIME);
                request.setAttribute(CacheConstants.DETAILS_USER_ID, user.getUserid());
                request.setAttribute(CacheConstants.DETAILS_USERNAME, user.getUsername());
                return user;
            }
            throw new LoginAuthorizeException("请重新登录！");
        }
        throw new LoginAuthorizeException("请先登录！");
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUser loginUser) {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken())) {
            refreshToken(loginUser);
        }
    }

    public void delLoginUser(String token) {
        if (StringUtils.isNotEmpty(token)) {
            String userKey = getTokenKey(token);
            redisService.deleteObject(userKey);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + EXPIRE_TIME * MILLIS_SECOND);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, EXPIRE_TIME, TimeUnit.SECONDS);
    }

    private String getTokenKey(String token) {
        return ACCESS_TOKEN + token;
    }
}
