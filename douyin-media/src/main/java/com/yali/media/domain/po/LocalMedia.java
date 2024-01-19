package com.yali.media.domain.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * minio视频资源
 *
 * @author fuqianlin
 * @date 2024-01-11 10:21
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("local_media")
public class LocalMedia implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键，文件id
     */
    @TableField(value = "id")
    private Long id;

    /**
     * 请求id
     */
    private String requestId;

    /**
     * 视频上传前的名称
     */
    private String originalMediaName;

    /**
     * 视频上传后的名称
     */
    private String uploadedMediaName;

    /**
     * 文件后缀
     */
    private String mediaSuffix;

    /**
     * 媒体播放地址
     */
    private String mediaUrl;

    /**
     * 媒资大小，单位字节
     */
    private Long mediaSize;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者
     */

    private Long creater;

    /**
     * 更新者
     */

    private Long updater;

    /**
     * 逻辑删除
     */
    private Integer deleted;
}
