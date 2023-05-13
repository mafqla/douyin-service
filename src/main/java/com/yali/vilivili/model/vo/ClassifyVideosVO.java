package com.yali.vilivili.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data

public class ClassifyVideosVO {

    @ApiModelProperty(name = "id", value = "视频id")
    private Long id;

    @ApiModelProperty(name = "title", value = "视频标题")
    private String title;

    @ApiModelProperty(name = "describe", value = "描述")
    private String describe;

    @ApiModelProperty(name = "videosCover", value = "视频封面")
    private String img;

    @ApiModelProperty(name = "description", value = "视频描述")
    private String description;

    @ApiModelProperty(name = "playNumber", value = "观看数量")
    private long playNumber;

    @ApiModelProperty(name = "playTime", value = "视频时长")
    private String playTime;

    @ApiModelProperty(name = "author", value = "视频更新时间")
    private String author;

    @ApiModelProperty(name = "rank", value = "排名")
    private int rank;
}
