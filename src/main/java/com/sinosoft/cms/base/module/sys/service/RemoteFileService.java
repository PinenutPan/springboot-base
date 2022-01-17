package com.sinosoft.cms.base.module.sys.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.sinosoft.cms.base.bean.SysFile;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件服务
 *
 * @author sinosoft
 */
public interface RemoteFileService {
    /**
     * 上传文件
     *
     * @param file 文件信息
     * @return 结果
     */
    public R<SysFile> upload(@RequestPart(value = "file") MultipartFile file);
}
