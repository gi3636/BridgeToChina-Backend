package com.btchina.file.controller;

import com.btchina.file.config.MinioConfig;
import com.btchina.file.util.MinioUtil;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RestController
@Api(tags = "文件管理")
@RequestMapping("/file")
public class FileController {

    @Resource
    private MinioConfig minioConfig;

    @Autowired
    private MinioUtil minioUtil;


    @ApiOperation(value = "上传文件")
    @PostMapping(value = "/upload")
    public ResponseEntity<HashMap<String, String>> uploadFile(MultipartFile file, @RequestParam(required = false) String bucketName) {
        String objectName = minioUtil.getDatePath() + file.getOriginalFilename();
        bucketName = StringUtils.hasLength(bucketName) ? bucketName : minioConfig.getDefaultBucketName();
        minioUtil.upload(bucketName, objectName, file);
        String viewPath = minioUtil.getPresignedObjectUrl(bucketName, objectName, 60 * 100, TimeUnit.SECONDS);
        HashMap<String, String> objectInfo = new HashMap<>();
        objectInfo.put("objectName", objectName);
        //只能预览图片、txt等部分文件
        objectInfo.put("viewPath", viewPath);
        return ResponseEntity.ok(objectInfo);
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
