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
 * 视频标签
 *
 * @author fuqianlin
 * @date 2023-04-16 13:48
 **/

@Data
@Entity
@Table(name = "videos_tag")
@DynamicInsert
@DynamicUpdate
public class VideosTagEntity {

    @Id
    @Column(name = "id") // 主键 ID
    private long id;

    @Column(name = "tag_name") // 标签名称
    private String tagName;

    @Column(name = "create_time") // 创建时间
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    @Column(name = "update_time") // 更新时间
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;
}
