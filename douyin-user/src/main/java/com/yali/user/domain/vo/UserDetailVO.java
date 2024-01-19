package com.yali.user.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "用户详情")
public class UserDetailVO {
    @ApiModelProperty(value = "用户id", example = "1")
    private Long uid;
    @ApiModelProperty(value = "名字", example = "张三")
    private String nickname;
    @ApiModelProperty(value = "头像", example = "default-icon.jpg")
    private String avatar_thumb;
    @ApiModelProperty(value = "背景图片", example = "default-bg.jpg")
    private String cover_url;
    @ApiModelProperty(value = "手机号", example = "13800010004")
    private String cellPhone;
    @ApiModelProperty(value = "抖音号", example = "12222214")
    private String unique_id;
    @ApiModelProperty(value = "个性签名")
    private String signature;
    @ApiModelProperty(value = "国家")
    private String country;
    @ApiModelProperty(value = "省")
    private String province;
    @ApiModelProperty(value = "市")
    private String city;
    @ApiModelProperty(value = "区")
    private String district;
    @ApiModelProperty(value = "ip属地")
    private String ip_location;
    @ApiModelProperty(value = "性别：1-男性，2-女性, 0-未知.", example = "0")
    private Integer gender;
    @ApiModelProperty(value = "生日", example = "2022-07-12")
    private LocalDate birthday;
    @ApiModelProperty(value = "学校")
    private String school;
    @ApiModelProperty(value = "用户状态：0-禁用，1-正常", example = "1")
    private Integer status;
    @ApiModelProperty(value = "认证类型", example = " 0 普通用户， 1 custom_verify， 2 enterprise_verify_reason")
    private Integer verification_type;
    @ApiModelProperty(value = "自定义认证", example = "演员")
    private String custom_verify;
    @ApiModelProperty(value = "企业认证", example = "1")
    private String enterprise_verify_reason;
    @ApiModelProperty(value = "注册时间", example = "2022-07-12")
    private LocalDateTime createTime;
    @ApiModelProperty(value = "角色id", example = "0-普通用户,1-管理员，2-超级管理员")
    private Long roleId;

    @ApiModelProperty(value = "喜欢作品数", example = "1")
    private Long favoriting_count;
    @ApiModelProperty(value = "粉丝数", example = "1")
    private Long follower_count;
    @ApiModelProperty(value = "关注数", example = "1")
    private Long following_count;
    @ApiModelProperty(value = "作品数", example = "1")
    private Long aweme_count;
    @ApiModelProperty(value = "粉丝最大数", example = "1")
    private Long max_follower_count;
    @ApiModelProperty(value = "获赞数量", example = "1")
    private Long total_favorited;
}
