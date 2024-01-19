package com.yali.vilivili.model.vo;

import com.yali.vilivili.model.entity.VideosEntity;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * 视频参数返回定义
 *
 * @author fuqianlin
 * @date 2023-07-31 15:49
 **/

@Data
public class VideosParamsVO {
    @ApiModelProperty(name = "videosList", value = "视频列表")
    private List<VideosInfoVO> videosList;

    @ApiModelProperty(name = "likeCount", value = "视频点赞数")
    private long likeCount;

    @ApiModelProperty(name = "collectCount", value = "视频收藏数")
    private long collectCount;

    @ApiModelProperty(name = "postCount", value = "视频发布数")
    private long publishCount;

}
