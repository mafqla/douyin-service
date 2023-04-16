package com.yali.vilivili.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分类列表
 *
 * @author fuqianlin
 * @date 2023-04-17 02:57
 **/

@Data
public class CategoriesVO {

    @ApiModelProperty(name = "id", value = "分类 ID", required = true)
    private Integer id;

    @ApiModelProperty(name = "img", value = "分类图标", required = true)
    private String img;

    @ApiModelProperty(name = "title", value = "分类名称", required = true)
    private String title;

    @ApiModelProperty(name = "description", value = "分类描述", required = false)
    private String description;
}
