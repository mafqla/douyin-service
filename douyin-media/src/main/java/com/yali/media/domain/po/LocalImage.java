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
 * @author fuqianlin
 * @date 2024-01-11 15:33
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("local_image")
public class LocalImage implements Serializable {

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
    private String originalImageName;

    /**
     * 视频上传后的名称
     */
    private String uploadedImageName;

    /**
     * 文件后缀
     */
    private String imageSuffix;

    /**
     * 媒体播放地址
     */
    private String imageUrl;

    /**
     * 媒资大小，单位字节
     */
    private Long imageSize;

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
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 逻辑删除
     */
    private Integer deleted;
}

