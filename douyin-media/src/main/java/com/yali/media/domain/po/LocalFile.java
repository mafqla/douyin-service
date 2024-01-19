package com.yali.media.domain.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * minio文件
 *
 * @author fuqianlin
 * @date 2024-01-11 09:57
 **/

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("local_file")
public class LocalFile implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键，文件id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 请求id
     */
    private String requestId;


    /**
     * 文件上传时的名称
     */
    private String originalFilename;

    /**
     * 文件上传后的名称
     */
    private String uploadedFilename;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文视频上传后地址
     */
    private String fileUrl;


    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    private Long creater;

    /**
     * 修改者
     */
    private Long updater;


    /**
     * 删除时间
     */
    private LocalDateTime deleteTime;

    /**
     * 逻辑删除标志，0表示未删除，1表示已删除
     */
    private Integer deleted;


}
