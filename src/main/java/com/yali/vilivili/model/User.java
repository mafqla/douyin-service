package com.yali.vilivili.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @Description 用户信息
 * @Date 2023.1.4 18:34
 * @Author fuqianlin
 */
@Data
@Entity
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
public class User {

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 用户密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 用户邮箱
     */
    @Column(name = "email")
    private String email;

    /**
     * 用户头像
     */
    @Column(name = "user_avatar")
    private String userAvatar;

    /**
     * 用户是否有效
     */
    @Column(name = "is_valid")
    private byte isValid;

    /**
     * 用户ip
     */
    @Column(name = "u_ip")
    private String uIp;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 用户类型
     * 默认值为0，0为普通用户，1为管理员
     */
    @Column(name = "type")
    private byte type;
}
