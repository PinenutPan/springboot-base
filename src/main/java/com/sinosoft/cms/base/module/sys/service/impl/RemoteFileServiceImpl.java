package com.sinosoft.cms.base.module.sys.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.sinosoft.cms.base.bean.SysFile;
import com.sinosoft.cms.base.module.sys.service.RemoteFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@Slf4j
public class RemoteFileServiceImpl implements RemoteFileService {
//    @Autowired
//    private AmazonS3 amazonS3;
//
//    @Value("${s3.bucket}")
//    private String bucketName;

    @Override
    public R<SysFile> upload(MultipartFile file) {
        String key = UUID.randomUUID().toString().replaceAll("-","") + ".png";
//        String url = uploadToS3(file, key);
        SysFile sysFile = new SysFile();
        sysFile.setName(key);
//        sysFile.setUrl(url);
        return R.ok(sysFile);
    }


//    public String uploadToS3(MultipartFile file, String key) {
//        try {
//            ObjectMetadata objectMetadata = new ObjectMetadata();
//            objectMetadata.setContentType("image/png");
//            amazonS3.putObject(new PutObjectRequest(bucketName, key, file.getInputStream(), objectMetadata)
//                    .withCannedAcl(CannedAccessControlList.PublicRead));
//            GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(bucketName, key);
//            urlRequest.setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000));
//            URL url = amazonS3.generatePresignedUrl(urlRequest);
//            return url.toString();
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//        }
//        return null;
//    }
}
