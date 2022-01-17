package com.sinosoft.cms.controller;

import com.sinosoft.cms.base.bean.AjaxResult;
import com.sinosoft.cms.base.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 测试的controller
 */
@RestController
@RequestMapping("/test")
@Api(value = "TestController", tags = "测试工具类")
@Slf4j
public class TestController {

    @GetMapping("/init")
    @ApiOperation(value = "init")
    public AjaxResult pingHostname() {
        Map<String,Object> map = new HashMap<>();
        try {
            String ip =  InetAddress.getLocalHost().getHostAddress();
            return AjaxResult.success(String.format("当前时间：%s，当前ip:%s", DateUtils.dateStrFormat(new Date(),"yyyy-MM-dd HH:mm:ss"),ip));
        } catch (UnknownHostException e) {
        }
        return AjaxResult.success();
    }
}
