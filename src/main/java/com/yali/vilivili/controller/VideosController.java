package com.yali.vilivili.controller;

import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.ro.VideosRo;
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
 * 视频接口
 *
 * @author fuqianlin
 * @date 2023-01-22 15:19
 **/

@RestController
@RequestMapping("/video")
@Api(value = "视频相关", tags = {"视频相关"})
public class VideosController extends BaseController {

    @Resource
    private FileUploadService fileUploadService;

    @ApiOperation(value = "视频上传")
    @PostMapping("/addVideo")
    public ResponseEntity<OR<Void>> videoUpload(@RequestParam("videos") MultipartFile videos, VideosRo videosRo){
        fileUploadService.videoUpload(videos, videosRo);
        return process(this::successResult);
    }
}
