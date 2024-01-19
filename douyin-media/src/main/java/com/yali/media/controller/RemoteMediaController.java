package com.yali.media.controller;


import com.yali.media.domain.dto.MediaDTO;
import com.yali.media.domain.dto.MediaUploadResultDTO;
import com.yali.media.domain.vo.VideoPlayVO;
import com.yali.media.service.IRemoteMediaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 媒资表，主要是视频文件 前端控制器
 * </p>
 */
@RestController
@RequestMapping("/medias")
@Api(tags = "媒资管理相关接口-远程调用")
@RequiredArgsConstructor
public class RemoteMediaController {

    private final IRemoteMediaService remoteMediaService;


//    @ApiOperation("分页搜索已上传媒资信息")
//    @GetMapping
//    public PageDTO<MediaVO> queryMediaPage(MediaQuery query){
//        return remoteMediaService.queryMediaPage(query);
//    }


    @ApiOperation("上传视频后保存媒资信息")
    @PostMapping
    public MediaDTO saveMedia(@RequestBody MediaUploadResultDTO result) {
        return remoteMediaService.save(result);
    }


    @ApiOperation("管理端获取预览视频的授权签名")
    @GetMapping("/signature/preview")
    public VideoPlayVO getPreviewSignature(
            @ApiParam(value = "媒资id", example = "1", required = true) @RequestParam("mediaId") Long mediaId) {
        return remoteMediaService.getPlaySignatureByMediaId(mediaId);
    }

    @ApiOperation("删除媒资视频")
    @DeleteMapping("{mediaId}")
    public void deleteMedia(
            @ApiParam(value = "媒资id", example = "1", required = true) @PathVariable("mediaId") Long mediaId) {
        remoteMediaService.removeById(mediaId);
    }

    @ApiOperation("批量删除媒资视频")
    @DeleteMapping
    public void deleteMedias(
            @ApiParam(value = "媒资id集合，例如1,2,3", required = true) @RequestParam("ids") List<Long> mediaIds) {
        remoteMediaService.removeByIds(mediaIds);
    }


}
