package com.yilena.yilenastudysystem.controller;

import com.yilena.yilenastudysystem.log.LogOperation;
import com.yilena.yilenastudysystem.pojo.Result;
import com.yilena.yilenastudysystem.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {

    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @LogOperation
    @PostMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}",file.getOriginalFilename());
        if(!file.isEmpty()){
            String originalFilename = file.getOriginalFilename();
            String extName = originalFilename.substring(originalFilename.lastIndexOf("."));
            String url = aliyunOSSOperator.upload(file.getBytes(),AliyunOSSOperator.generateUniqueName(extName));
            return Result.success(url);
        }
        return Result.error("上传失败");
    }
}
