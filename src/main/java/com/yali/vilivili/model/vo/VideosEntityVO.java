package com.yali.vilivili.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;

@Data
public class VideosEntityVO {

    @ApiModelProperty(name = "id", value = "视频id")
    private Long id;

    @ApiModelProperty(name = "title", value = "视频标题")
    private String title;

    @ApiModelProperty(name = "videosCover", value = "视频封面")
    private String videosCover;

    @ApiModelProperty(name = "description", value = "视频描述")
    private String description;

    @ApiModelProperty(name = "videosTime", value = "视频时长")
    private String videosTime;

    @ApiModelProperty(name = "videosAddress", value = "视频地址")
    private String videosAddress;

    @ApiModelProperty(name = "uploadTime", value = "视频更新时间")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date uploadTime;

    @ApiModelProperty(name = "playCount", value = "观看次数")
    private long playCount;

    @ApiModelProperty(name = "likeCount", value = "点赞数")
    private long likeCount;

    @ApiModelProperty(name = "dislikeCount", value = "不喜欢数量")
    private long dislikeCount;

    @ApiModelProperty(name = "commentCount", value = "评论数量")
    private long commentCount;

    @ApiModelProperty(name = "status", value = "视频状态 默认为0对所有人可见，1仅粉丝可见，2仅自己可见，3置顶")
    //默认为0对所有人可见，1仅粉丝可见，2仅自己可见，3置顶
    private Integer status;

    @ApiModelProperty(name = "authorName", value = "作者名")
    private String authorName;

    @ApiModelProperty(name = "commentList", value = "评论列表")
    private List<String> commentList;

    @ApiModelProperty(name = "collectionCount", value = "收藏数量")
    private long collectionCount;

    @ApiModelProperty(name = "authorAvatar", value = "作者头像")
    private String authorAvatar;

    @ApiModelProperty(name = "tagList", value = "标签名列表")
    private List<String> tagNameList;
}
