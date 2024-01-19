package com.yali.user.domain.query;

import com.yali.common.domain.query.PageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(description = "用户分页查询条件")
public class UserPageQuery extends PageQuery {
    @ApiModelProperty(value = "账户状态")
    private Integer status;
    @ApiModelProperty(value = "用户名称")
    private String name;
    @ApiModelProperty(value = "手机号码")
    private String phone;
}
