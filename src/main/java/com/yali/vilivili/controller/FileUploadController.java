package com.yali.vilivili.controller;

import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.service.FileUploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 文件上传接口
 *
 * @author fuqianlin
 * @date 2023-01-18 21:58
 **/

@RestController
@RequestMapping("/upload")
@Api(value = "文件相关", tags = {"文件相关"})
public class FileUploadController extends BaseController {

    @Resource
    private FileUploadService fileUploadService;



    @ApiOperation(value = "图片上传")
    @PostMapping("/image")
    public ResponseEntity<OR<String>> imageUpload(@RequestParam("file") MultipartFile file){
        return processData(() -> fileUploadService.imageUpload(file),"上传成功", this::processException);    }



}
