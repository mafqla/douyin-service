package com.yali.vilivili.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 视频信息
 *
 * @author fuqianlin
 * @date 2023-07-31 20:32
 **/

@Data
public class VideosInfoVO {

    @ApiModelProperty(name = "id", value = "视频id")
    private Long id;

    @ApiModelProperty(name = "title", value = "视频标题")
    private String title;

    @ApiModelProperty(name = "description", value = "视频描述")
    private String description;

    @ApiModelProperty(name = "videosCover", value = "视频封面")
    private String videosCover;

    @ApiModelProperty(name = "videosAddress", value = "视频地址")
    private String videosAddress;

    @ApiModelProperty(name = "videosTime", value = "视频时长")
    private String videosTime;

    @ApiModelProperty(name = "uploadTime", value = "上传时间")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date uploadTime;

    @ApiModelProperty(name = "playCount", value = "播放量")
    private Long playCount;

    @ApiModelProperty(name = "likeCount", value = "点赞量")
    private Long likeCount;

    @ApiModelProperty(name = "dislikeCount", value = "点踩量")
    private long dislikeCount;

    @ApiModelProperty(name = "commentCount", value = "评论量")
    private Long commentCount;

    @ApiModelProperty(name = "status", value = "视频状态")
    private Integer status;

    @ApiModelProperty(name = "isTop", value = "是否置顶")
    private Boolean isTop;

    @ApiModelProperty(name = "isAttention", value = "是否关注")
    private Boolean isAttention;

    @ApiModelProperty(name = "isLike", value = "是否点赞")
    private Boolean isLike;

    @ApiModelProperty(name = "isDislike", value = "是否点踩")
    private Boolean isDislike;

    @ApiModelProperty(name = "isCollect", value = "是否收藏")
    private Boolean isCollect;

    @ApiModelProperty(name = "userName", value = "用户名")
    private String userName;

    @ApiModelProperty(name = "userAvatar", value = "用户头像")
    private String userAvatar;
}
