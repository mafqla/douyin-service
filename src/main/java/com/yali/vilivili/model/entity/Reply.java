package com.yali.vilivili.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author yujie
 * @createTime 2023/5/13 15:55
 * @description 回复表
 */
@Data
@TableName("t_reply")
public class Reply {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "vid")
    private long vid;

    @TableField(exist = false)
    private VideosEntity videosEntity;

    @TableField(value = "cid")
    private long cid;

    @TableField(exist = false)
    private CommentEntity commentEntity;

    @TableField(value = "fromUid")
    private Integer fromUid;

    @TableField(value = "ip")
    private String ip;

    @TableField(exist = false)
    private UserEntity fromUser;

    @TableField(value = "toUid")
    private Integer toUid;

    @TableField(exist = false)
    private UserEntity toUser;

    @TableField(value = "msg")
    private String msg;

    @TableField(value = "date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date;

}
