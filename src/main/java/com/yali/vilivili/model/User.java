package com.yali.vilivili.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;

/**
 * @Description 用户信息
 * @Date 2023/1/3 21:13
 * @Author pq
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
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "username")
    private String username;

    /**
     * 用户名
     */
    @Column(name = "password")
    private String password;

    /**
     * 用户名
     */
    @Column(name = "email")
    private String email;

    /**
     * 用户名
     */
    @Column(name = "is_valid")
    private int isValid;

    /**
     * 用户名
     */
    @Column(name = "u_ip")
    private String uIp;

    /**
     * 用户名
     */
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 用户名
     */
    @JsonFormat(pattern = "yyyy.MM.dd HH:mm:ss", timezone = "GMT+08:00")
    @Column(name = "update_time")
    private Date updateTime;

    /**
     * 用户名
     */
    @Column(name = "type")
    private int type;
}
