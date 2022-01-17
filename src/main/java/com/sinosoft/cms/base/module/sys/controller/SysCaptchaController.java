package com.sinosoft.cms.base.module.sys.controller;

import com.sinosoft.cms.base.bean.AjaxResult;
import com.sinosoft.cms.base.config.RedisService;
import com.sinosoft.cms.base.constant.Constants;
import com.sinosoft.cms.base.controller.BaseController;
import com.sinosoft.cms.base.utils.Base64;
import com.sinosoft.cms.base.utils.uuid.IdUtils;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 图片验证码（支持算术形式）
 */
@Controller
@RequestMapping("/captcha")
@Api(value = "SysCaptchaController", tags = "验证码管理")
public class SysCaptchaController extends BaseController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    // 验证码类型
    private String captchaType = "math";

    @Resource
    private RedisService redisService;

    /**
     * 验证码生成
     */
    @GetMapping(value = "/captchaImage")
    @ResponseBody
    @ApiOperation("获取验证码")
    public AjaxResult getKaptchaImage(HttpServletRequest request, HttpServletResponse response) {

        String captchaType = request.getParameter("type");
        if (captchaType == null || captchaType.trim().equals("")) {
            captchaType = this.captchaType;
        }
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;

        String capStr = null, code = null;
        BufferedImage image = null;

        // 生成验证码
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisService.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return AjaxResult.error(e.getMessage());
        }

        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }
}
