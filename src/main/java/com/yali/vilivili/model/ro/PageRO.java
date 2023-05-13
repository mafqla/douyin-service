package com.yali.vilivili.model.ro;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 *
 * @author fuqianlin
 * @date 2023-01-22 15:11
 **/

@Data
public class PageRO {
    @ApiModelProperty(name = "currentPage", value = "当前页", required = true)
    private long currentPage;
    @ApiModelProperty(name = "pageSize", value = "当前页数据条数", required = true)
    private long pageSize;

}
