package com.yali.vilivili.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 视频分类表
 *
 * @author fuqianlin
 * @date 2023-04-16 13:28
 **/

@Data
@Entity
@Table(name = "videos_categories")
@DynamicInsert
@DynamicUpdate
public class VideosCategories {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "img")
    private String img;

    @Column(name = "description")
    private String description;

    @Column(name = "create_time")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    @Column(name = "update_time")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;
}
