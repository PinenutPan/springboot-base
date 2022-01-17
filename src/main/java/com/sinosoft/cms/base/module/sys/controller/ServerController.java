package com.sinosoft.cms.base.module.sys.controller;

import com.sinosoft.cms.base.annotation.PreAuthorize;
import com.sinosoft.cms.base.controller.BaseController;
import com.sinosoft.cms.base.web.domain.Server;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 服务器监控
 */
@Controller
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {

    @PreAuthorize(hasAnyPermi = "monitor:server:server")
    @GetMapping("/server")
    @ResponseBody
    public Server server() throws Exception {
        Server server = new Server();
        server.copyTo();
        return server;
    }
}
