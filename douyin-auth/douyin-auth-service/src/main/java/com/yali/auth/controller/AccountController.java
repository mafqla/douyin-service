package com.yali.auth.controller;


import com.yali.api.dto.user.LoginFormDTO;
import com.yali.api.dto.user.RegisterFormDTO;
import com.yali.auth.common.constants.JwtConstants;
import com.yali.auth.service.IAccountService;
import com.yali.common.exceptions.BadRequestException;
import com.yali.common.utils.WebUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 账户登录相关接口
 */
@RestController
@RequestMapping("/accounts")
@Api(tags = "账户管理")
@RequiredArgsConstructor
public class AccountController {

    private final IAccountService accountService;

    @ApiOperation("登录并获取token")
    @PostMapping(value = "/login")
    public Map<String, String> loginByPw(LoginFormDTO loginFormDTO) {
        return accountService.login(loginFormDTO);
    }

    @ApiOperation("管理端登录并获取token")
    @PostMapping(value = "/admin/login")
    public Map<String, String> adminLoginByPw(@RequestBody LoginFormDTO loginFormDTO) {
        return accountService.login(loginFormDTO);
    }

    @ApiOperation("退出登录")
    @PostMapping(value = "/logout")
    public void logout() {
        accountService.logout();
    }

    @ApiOperation("刷新token")
    @GetMapping(value = "/refresh")
    public String refreshToken(
            @CookieValue(value = JwtConstants.REFRESH_HEADER, required = false) String commonToken,
            @CookieValue(value = JwtConstants.ADMIN_REFRESH_HEADER, required = false) String adminToken
    ) {
        if (commonToken == null && adminToken == null) {
            throw new BadRequestException("登录超时");
        }
        String host = WebUtils.getHeader("origin");
        if (host == null) {
            throw new BadRequestException("登录超时");
        }
        String token = host.startsWith("www", 7) ? commonToken : adminToken;
        if (token == null) {
            throw new BadRequestException("登录超时");
        }
        return accountService.refreshToken(WebUtils.cookieBuilder().decode(token));
    }

    @ApiOperation("用户注册")
    @PostMapping(value = "/register")
    public Map<String, String> register(@RequestBody RegisterFormDTO registerFormDTO) {
        return accountService.register(registerFormDTO);
    }

    // todo: 添加验证码接口 /captcha

//    @ApiOperation("获取验证码")
//    @GetMapping(value = "/captcha")
//    public String getCaptcha() {
//        return accountService.getCaptcha();
//    }
}
