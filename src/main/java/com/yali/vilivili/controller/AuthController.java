package com.yali.vilivili.controller;

import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.ro.LoginRO;
import com.yali.vilivili.model.vo.LoginVO;
import com.yali.vilivili.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Description 登录注册接口
 * @Date 2022/11/16 22:45
 * @Author pq
 */
@RestController
@RequestMapping("/auth")
@Api(value = "登录相关", tags = {"登录相关"})
public class AuthController extends BaseController {
    @Resource
    private LoginService loginService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ResponseEntity<OR<LoginVO>> login(@Validated LoginRO ro, BindingResult br){
        return this.processData(()->loginService.login(ro),br,"登录成功",this::processException);
    }
}
