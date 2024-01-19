package com.yali.user.controller;

import com.yali.user.service.ICodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author fuqianlin
 * @date 2023-11-17 16:08
 **/
@RestController
@Api(tags = "验证码接口")
@RequestMapping("/code")
public class CodeController {

    @Resource
    private ICodeService codeService;

    @ApiOperation("发送验证码")
    @GetMapping("/send")
    public void sendCode(@ApiParam("手机号") String phone) {
        codeService.sendVerifyCode(phone);
    }

}
