package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VideosClassifyRO extends PageRO{

    @ApiModelProperty(name = "tagId", value = "标签id", required = true)
    private long tagId;

}
