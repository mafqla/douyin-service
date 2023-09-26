package com.yali.vilivili.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户信息
 *
 * @author fuqianlin
 * @date 2023-09-24 18:11
 **/

@Data
public class UserInfoVO {

    @ApiModelProperty(name = "id", value = "用户id")
    private Integer id;

    @ApiModelProperty(name = "username", value = "用户名")
    private String username;

    @ApiModelProperty(name = "userNum", value = "用户编号")
    private String userNum;

    @ApiModelProperty(name = "avatar", value = "头像")
    private String avatar;

    @ApiModelProperty(name = "userSignature", value = "个性签名")
    private String userSignature;

    @ApiModelProperty(name = "gender", value = "性别")
    private String gender;

    @ApiModelProperty(name = "birthday", value = "生日")
    private String birthday;

    //用户学校
    @ApiModelProperty(name="school", value = "用户学校")
    private String school;

    //用户类型
    @ApiModelProperty(name="userType", value = "用户类型")
    private String userType;


    @ApiModelProperty(name = "address", value = "地址")
    private String address;

    @ApiModelProperty(name = "ipAddress", value = "ip属地")
    private String ipAddress;

    @ApiModelProperty(name = "fansCount", value = "粉丝数")
    private Long fansCount;

    @ApiModelProperty(name = "attentionCount", value = "关注数")
    private Long attentionCount;

    @ApiModelProperty(name="likeCount", value = "获取点赞数")
    private Long likeCount;

    //用户喜欢的视频数
    @ApiModelProperty(name="likeVideosCount", value = "用户喜欢的视频数")
    private Long likeVideosCount;

    //用户上传的视频数
    @ApiModelProperty(name="uploadVideosCount", value = "用户上传的视频数")
    private Long uploadVideosCount;

    //用户收藏的视频数
    @ApiModelProperty(name="collectVideosCount", value = "用户收藏的视频数")
    private Long collectVideosCount;

    //是否关注是否关注 0-自己 1-已关注 2-未关注 3-互相关注
    @ApiModelProperty(name="isAttention", value = "是否关注 0-自己 1-已关注 2-未关注 3-互相关注")
    private Integer isAttention;
    //用户标签
    @ApiModelProperty(name = "user_auth", value = "关注的用户认证", required = true)
    private String userAuth;

    //用户标签类型
    @ApiModelProperty(name = "user_auth_type", value = "关注的用户认证类型", required = true)
    private String userAuthType;
}
