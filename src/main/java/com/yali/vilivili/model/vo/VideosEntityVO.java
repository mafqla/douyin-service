package com.yali.vilivili.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class VideosEntityVO {

    private Long id;

    private String title;

    private String videosCover;

    private String description;


    private String videosTime;

    private String videosAddress;

    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date uploadTime;

    private long playCount;

    private long likeCount;

    private long dislikeCount;

    private long commentCount;

    //默认为0对所有人可见，1仅粉丝可见，2仅自己可见，3置顶
    private Integer status;

    private String authorName;
}
