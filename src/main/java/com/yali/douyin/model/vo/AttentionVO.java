package com.yali.vilivili.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 关注
 *
 * @author fuqianlin
 * @date 2023-09-25 14:03
 **/
@Data
public class AttentionVO {

    @ApiModelProperty(name = "id", value = "关注id", required = true)
    private Long id;

    @ApiModelProperty(name = "nickname", value = "关注的用户昵称", required = true)
    private String nickname;

    @ApiModelProperty(name = "avatar", value = "关注的用户头像", required = true)
    private String avatar;

    // 个性签名
    @ApiModelProperty(name = "signature", value = "关注的用户个性签名", required = true)
    private String signature;

    //用户标签
    @ApiModelProperty(name = "user_auth", value = "关注的用户认证", required = true)
    private String userAuth;

    //是否关注 0-自己 1-已关注 2-未关注 3-互相关注
    @ApiModelProperty(name = "is_attention", value = "是否关注 0-自己 1-已关注 2-未关注 3-互相关注", required = true)
    private Integer isAttention;

    //用户标签类型
    @ApiModelProperty(name = "user_auth_type", value = "关注的用户认证类型", required = true)
    private String userAuthType;


}
