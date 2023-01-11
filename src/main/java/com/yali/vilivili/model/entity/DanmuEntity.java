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
@Table(name = "danmu")
@DynamicInsert
@DynamicUpdate
public class DanmuEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "video_id")
    private long videoId;

    @Column(name = "send_time")
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date sendTime;
    @Column(name = "message")
    private String message;
}
