package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideosClassifyRO extends PageRO{

    @ApiModelProperty(name = "tagName", value = "标签名", required = true)
    private String tagName;

}
