package com.btchina.file.controller;

import com.btchina.core.api.CommonResult;
import com.btchina.file.config.MinioConfig;
import com.btchina.file.util.MinioUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "文件管理")
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Resource
    private MinioConfig minioConfig;

    @Autowired
    private MinioUtil minioUtil;


    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload")
    public CommonResult<HashMap<String, String>> uploadFile(MultipartFile file, @RequestParam(required = false) String bucketName) throws Exception {
        bucketName = StringUtils.hasLength(bucketName) ? bucketName : minioConfig.getDefaultBucketName();
        log.info("minioConfig:{}", minioConfig);
        log.info("上传文件到桶：{}，文件名：{}", bucketName, file.getOriginalFilename());
        String objectName = minioUtil.getDatePath() + file.getOriginalFilename();
        //判断是否有桶，没有则创建
        if (!minioUtil.bucketExists(bucketName)) {
            minioUtil.createBucket(bucketName);
        }
        minioUtil.upload(bucketName, objectName, file);
        String viewPath = minioUtil.getPresignedObjectUrl(bucketName, objectName, 60 * 100, TimeUnit.SECONDS);
        HashMap<String, String> objectInfo = new HashMap<>();
        objectInfo.put("path", bucketName + objectName);
        //只能预览图片、txt等部分文件
        objectInfo.put("viewPath", viewPath);
        return CommonResult.success(objectInfo);
    }


    @ApiOperation(value = "删除文件")
    @PostMapping(value = "/delete")
    public ResponseEntity<String> deleteByPath(@RequestParam(required = false) String bucketName, String objectName) {
        bucketName = StringUtils.hasLength(bucketName) ? bucketName : minioConfig.getDefaultBucketName();
        minioUtil.delete(bucketName, objectName);
        return ResponseEntity.ok("删除成功");
    }

    @ApiOperation(value = "下载文件")
    @GetMapping("/download")
    public void downLoad(@RequestParam(required = false) String bucketName, String objectName, HttpServletResponse response) {
        // 获取文件
        minioUtil.downResponse(bucketName, objectName, response);
    }
}
