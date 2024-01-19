package com.yali.vilivili.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * 视频信息
 *
 * @author fuqianlin
 * @date 2023-04-16 13:44
 **/

@Data
@Entity
@Table(name = "videos_info")
@DynamicInsert
@DynamicUpdate
@TableName(value = "videos_info")
public class VideosInfoEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // 视频 ID
    @Column(name = "video_id")
    private long videoId; // 视频 ID

    @Column(name = "user_id")
    private long userId; // 用户 ID


    @Column(name = "tag_id")
    private long tagId; // 标签 ID

    @Column(name = "categories_id")
    private long categoriesId; // 分类 ID


}
