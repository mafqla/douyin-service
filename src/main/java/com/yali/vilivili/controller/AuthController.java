package com.yali.vilivili.controller;

import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.ro.EmailRO;
import com.yali.vilivili.model.ro.LoginRO;
import com.yali.vilivili.model.ro.RegisterRO;
import com.yali.vilivili.model.vo.LoginVO;
import com.yali.vilivili.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Description 登录注册接口
 * @Date 2022/11/16 22:45
 * @Author pq
 */
@RestController
@RequestMapping("/auth")
@Api(value = "账号相关", tags = {"账户相关"})
public class AuthController extends BaseController {
    @Resource
    private AuthService authService;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ResponseEntity<OR<LoginVO>> login(@Validated LoginRO ro, BindingResult br){
        return this.processData(()->authService.login(ro),br,"登录成功",this::processException);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public ResponseEntity<OR<Void>> logout(HttpServletRequest request){
        String token = request.getHeader("loginToken");
        authService.logout(token);
        return process(this::successResult);
    }

    @ApiOperation(value = "发送验证码")
    @PostMapping("/sendCode")
    public ResponseEntity<OR<Void>> sendCode(@Valid EmailRO ro){
        authService.sendEmailCode(ro);
        return process(this::successResult);
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public ResponseEntity<OR<Void>> register(@Valid RegisterRO ro){
        authService.register(ro);
        return process(this::successResult);
    }

    @ApiOperation(value = "重置密码")
    @PostMapping("/resetPassword")
    public ResponseEntity<OR<Void>> resetPassword(String email,  String password, String code){
        authService.resetPassword(email,password,code);
        return process(this::successResult);
    }

    @ApiOperation(value = "修改用户头像")
    @PostMapping("/updateAvatar")
    public ResponseEntity<OR<Void>> updateAvatar(String email, String avatar){
        authService.updateAvatar(email,avatar);
        return process(this::successResult);
    }

}
