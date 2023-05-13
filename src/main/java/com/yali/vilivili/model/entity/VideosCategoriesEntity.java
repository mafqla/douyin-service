package com.yali.vilivili.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName(value="videos_categories")
public class VideosCategoriesEntity {

    @Id
    @Column(name = "id") // 主键 ID
    private Integer id;

    @Column(name = "img") // 图片 URL
    private String img;

    @Column(name = "categories_name") // 分类名称
    private String categoriesName;

    @Column(name = "description") // 描述信息
    private String description;

    @Column(name = "create_time") // 创建时间
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    @Column(name = "update_time") // 更新时间
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;
}
