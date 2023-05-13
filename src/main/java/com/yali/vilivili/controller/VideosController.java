package com.yali.vilivili.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.mapper.VideosEntityMapper;
import com.yali.vilivili.mapper.VideosInfoEntityMapper;
import com.yali.vilivili.model.entity.VideosEntity;
import com.yali.vilivili.model.entity.VideosInfoEntity;
import com.yali.vilivili.model.ro.VideosPageRO;
import com.yali.vilivili.model.ro.VideosRo;
import com.yali.vilivili.service.FileUploadService;
import com.yali.vilivili.service.VideosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
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

    @Resource
    VideosInfoEntityMapper videosInfoEntityMapper;

    @Resource
    VideosEntityMapper videosEntityMapper;

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

    @ApiOperation(value = "分页获取视频列表")
    @PostMapping("/getVideosListByPage")
    public ResponseEntity<OR<List<VideosEntity>>> getVideosListByPage(Integer page, Integer size, Integer status) {
        videosService.getVideosListByPage(page, size, status);
        return processData(() -> videosService.getVideosListByPage(page, size, status), "获取成功", this::processException);
    }

    @ApiOperation(value = "视频滚动加载接口")
    @PostMapping("/getVideosByCursor")
    public ResponseEntity<OR<List<VideosEntity>>> getVideosByCursor(Integer cursor, Integer size, Integer status) {
        videosService.getVideosListByCursor(cursor, size, status);
        return processData(() -> videosService.getVideosListByCursor(cursor, size, status), "获取成功", this::processException);
    }

    @ApiOperation(value = "分类视频")
    @PostMapping("/getVideosByTag")
    public ResponseEntity<OR<List<VideosEntity>>> getVideosByTag(@RequestBody VideosPageRO ro) {
        QueryWrapper<VideosInfoEntity> videosInfoEntityQueryWrapper = new QueryWrapper<>();
        videosInfoEntityQueryWrapper.eq("tag_id", ro.getTagId());
        List<VideosInfoEntity> videosInfoEntities = videosInfoEntityMapper.selectList(videosInfoEntityQueryWrapper);
        List<Long> videoIdList = new ArrayList<>();
        videosInfoEntities.forEach(videosInfoEntity -> {
            videoIdList.add(videosInfoEntity.getVideoId());
        });
        Page<VideosEntity> page = new Page<>(ro.getCurrentPage(), ro.getPageSize());
        QueryWrapper<VideosEntity> videosEntityQueryWrapper = new QueryWrapper<>();
        videosEntityQueryWrapper.in("id", videoIdList);
        IPage<VideosEntity> videosEntityIPage = videosEntityMapper.selectPage(page, videosEntityQueryWrapper);
        return processData(videosEntityIPage::getRecords, "获取分类视频成功", this::processException);
    }
}
