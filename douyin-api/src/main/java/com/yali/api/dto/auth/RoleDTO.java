package com.yali.api.dto.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(description = "角色实体")
public class RoleDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键", example = "1")
    private Long id;

    /**
     * 角色代号，例如：admin
     */
    @ApiModelProperty(value = "角色代号", example = "admin")
    private String code;

    /**
     * 角色描述
     */
    @ApiModelProperty(value = "角色名称", example = "管理员")
    private String name;

    /**
     * 角色类型
     */
    @ApiModelProperty(value = "角色类型", example = "0-固定角色（不可选）1-自定义角色")
    private Integer type;

    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人", example = "用户12")
    private String creater;

    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人", example = "admin")
    private String updater;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2020-01-01 00:00:00")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间", example = "2020-01-01 00:00:00")
    private LocalDateTime updateTime;
}
