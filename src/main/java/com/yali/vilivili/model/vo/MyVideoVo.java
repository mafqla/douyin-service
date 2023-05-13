package com.yali.vilivili.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class MyVideoVo {
    @ApiModelProperty(name = "id", value = "视频id")
    private Long id;

    @ApiModelProperty(name = "title", value = "视频标题")
    private String title;

    @ApiModelProperty(name = "videosCover", value = "视频封面")
    private String videosCover;

    @ApiModelProperty(name = "description", value = "视频描述")
    private String description;

    @ApiModelProperty(name = "likeCount", value = "点赞数")
    private long likeCount;
}
