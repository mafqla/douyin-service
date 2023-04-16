package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.jpa.repository.Modifying;

import javax.validation.constraints.NotBlank;

/**
 * 添加视频分类分类
 *
 * @author fuqianlin
 * @date 2023-04-17 00:41
 **/

@Data
public class AddCategoriesRO {

    @ApiModelProperty(name = "img", value = "分类图标", required = true)
    private String img;

    @ApiModelProperty(name = "categoriesName", value = "分类名称", required = true)
    @NotBlank(message = "分类名称不能为空")
    private String categoriesName;

    @ApiModelProperty(name = "description", value = "分类描述", required = false)
    private String description;


}
