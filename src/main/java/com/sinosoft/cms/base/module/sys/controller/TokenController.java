package com.sinosoft.cms.base.module.sys.controller;

import com.sinosoft.cms.base.bean.LoginBody;
import com.sinosoft.cms.base.bean.LoginUser;
import com.sinosoft.cms.base.bean.R;
import com.sinosoft.cms.base.config.RedisService;
import com.sinosoft.cms.base.config.TokenService;
import com.sinosoft.cms.base.constant.Constants;
import com.sinosoft.cms.base.exception.CaptchaException;
import com.sinosoft.cms.base.module.sys.service.SysLoginService;
import com.sinosoft.cms.base.utils.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * token 控制
 *
 * @author sinosoft
 */
@RestController
@Api(value = "TokenController", tags = "登录管理")
@Slf4j
public class TokenController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SysLoginService sysLoginService;

    @Autowired
    private RedisService redisService;


    @PostMapping("login")
    @ApiOperation("登录")
    public R<?> login(@RequestBody LoginBody form) {
        checkCapcha(form.getValidateCode(), form.getUuid());
        LoginUser userInfo = null;
        // 用户登录
        userInfo = sysLoginService.login(form.getUsername(), form.getPassword());
        Assert.notNull(userInfo, "系统异常，请联系管理员！");
        // 获取登录token
        return R.ok(tokenService.createToken(userInfo));
    }

    @DeleteMapping("logout")
    @ApiOperation("退出登录")
    public R<?> logout(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            String username = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            sysLoginService.logout(username);
        }
        return R.ok();
    }

    @PostMapping("refresh")
    @ApiOperation("刷新登录")
    public R<?> refresh(HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser)) {
            // 刷新令牌有效期
            tokenService.refreshToken(loginUser);
            return R.ok();
        }
        return R.ok();
    }


    public void checkCapcha(String code, String uuid) throws CaptchaException {
        if (StringUtils.isEmpty(code)) {
            throw new CaptchaException("验证码不能为空");
        }
        if (StringUtils.isEmpty(uuid)) {
            throw new CaptchaException("验证码已失效");
        }
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String captcha = redisService.getCacheObject(verifyKey);
        redisService.deleteObject(verifyKey);

        if (!code.equalsIgnoreCase(captcha)) {
            throw new CaptchaException("验证码错误");
        }
    }
}
