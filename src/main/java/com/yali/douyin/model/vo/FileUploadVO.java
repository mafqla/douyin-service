package com.yali.vilivili.model.vo;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

/**
 * 文件上传返回值
 *
 * @author fuqianlin
 * @date 2023-01-20 11:30
 **/

@Data
public class FileUploadVO {


    /**
     * 文件路径
     *
     */
    @ApiModelProperty(value = "文件路径")
    private String path;


    /**
     * 文件url
     */
    @ApiModelProperty(value = "文件url")
    private String url;
}
