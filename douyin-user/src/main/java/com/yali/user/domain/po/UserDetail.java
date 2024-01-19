package com.yali.user.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yali.common.enums.UserType;
import jdk.jfr.Description;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 详情表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user_detail")
public class UserDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 关联用户id
     */
    @TableId(value = "id", type = IdType.NONE)
    private Long id;

    /**
     * 认证类型
     */
    @Description(value = "认证类型:0 普通用户， 1 custom_verify， 2 enterprise_verify_reason")
    private Integer verificationType;

    /**
     * 自定义认证
     */
    @Description("自定义认证")
    private String customVerify;
    /**
     * 企业认证
     */
    private String enterpriseVerifyReason;


    /**
     * 性别：0-男性，1-女性
     */
    private Integer gender;

    /**
     * 头像地址
     */
    private String avatarThumb;

    /**
     * 邮箱
     */
    private String email;

    /**
     * QQ号码
     */
    private String qq;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 学校
     */
    private String school;

    /**
     * 国家
     */
    private String country;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 区
     */
    private String district;

    /**
     * 个人介绍
     */
    private String signature;

    /**
     * 背景
     */
    private String coverUrl;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * ipv4地址
     */
    private String ipv4;

    /**
     * ipv6地址
     */
    private String ipv6;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 创建者id
     */
    private Long creater;

    /**
     * 更新者id
     */
    private Long updater;


    @TableField(exist = false)
    private String cellPhone;
    @TableField(exist = false)
    private String nickname;
    @TableField(exist = false)
    private Integer status;
    @TableField(exist = false)
    private String uniqueId;
}
