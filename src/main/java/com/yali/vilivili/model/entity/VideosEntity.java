package com.yali.vilivili.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author fuqianlin
 * @date 2023-01-09 18:25
 **/

@Data
@Entity
@Table(name = "videos")
@DynamicInsert
@DynamicUpdate
public class VideosEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "videos_cover")
    private String videosCover;

    @Column(name = "description")
    private String description;


    @Column(name = "videos_time")
    private String videosTime;

    @Column(name = "videos_address")
    private String videosAddress;

    @Column(name = "upload_time")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date uploadTime;

    @Column(name = "play_count")
    private long playCount;

    @Column(name = "like_count")
    private long likeCount;

    @Column(name = "dislike_count")
    private long dislikeCount;

    @Column(name = "comment_count")
    private long commentCount;

    //默认为0对所有人可见，1仅粉丝可见，2仅自己可见，3置顶
    @Column(name = "status")
    private Integer status;
}
