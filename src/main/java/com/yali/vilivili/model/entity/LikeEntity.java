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
@Table(name = "like")
@DynamicInsert
@DynamicUpdate
public class LikeEntity {

    @Id
    @Column(name = "user_id")
    private long userId;

    @Column(name = "video_id")
    private long videoId;
    @Basic
    @Column(name = "like_time")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date likeTime;
}
