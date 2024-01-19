package com.yali.api.dto.sms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import java.util.Map;

@Data
@ApiModel(description = "短信发送参数")
public class SmsInfoDTO {
    @ApiModelProperty(value = "短信模板code", example = "SMS_123456")
    private String templateCode;
    @ApiModelProperty(value = "手机号码", example = "['12345678901','12345678902']")
    private Iterable<String> phones;
    @ApiModelProperty(value = "模板参数", example = "{'code':'123456'}")
    private Map<String, String> templateParams;
}
