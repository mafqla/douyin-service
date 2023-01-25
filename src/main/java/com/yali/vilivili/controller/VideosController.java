package com.yali.vilivili.controller;

import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.entity.VideosEntity;
import com.yali.vilivili.model.ro.VideosRo;
import com.yali.vilivili.service.FileUploadService;
import com.yali.vilivili.service.VideosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

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

    @Resource
    private VideosService videosService;

    @ApiOperation(value = "视频上传")
    @PostMapping("/addVideo")
    public ResponseEntity<OR<Void>> videoUpload(@RequestParam("videos") MultipartFile videos, VideosRo videosRo) {
        fileUploadService.videoUpload(videos, videosRo);
        return process(this::successResult);
    }

    @ApiOperation(value = "获取视频列表")
    @GetMapping("/getVideosList")
    public ResponseEntity<OR<List<VideosEntity>>> getVideosList() {
        videosService.getVideosList();
        return processData(() -> videosService.getVideosList(), "获取成功", this::processException);
    }

    @ApiOperation(value = "获取视频列表")
    @PostMapping("/getVideosListByPage")
    public ResponseEntity<OR<List<VideosEntity>>> getVideosListByPage(Integer page, Integer size, Integer status) {
        videosService.getVideosListByPage(page, size, status);
        return processData(() -> videosService.getVideosListByPage(page, size, status), "获取成功", this::processException);
    }
}
