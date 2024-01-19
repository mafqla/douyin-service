package com.yali.message.domain.query;

import com.yali.common.domain.query.PageQuery;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@ApiModel(description = "通知模板查询对象")
@Data
public class SmsThirdPlatformPageQuery extends PageQuery {
    private Integer status;
    private String keyword;
}
