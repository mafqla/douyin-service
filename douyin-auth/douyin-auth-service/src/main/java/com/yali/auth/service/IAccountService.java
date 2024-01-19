package com.yali.auth.service;

import com.yali.api.dto.user.LoginFormDTO;
import com.yali.api.dto.user.RegisterFormDTO;

import java.util.Map;

/**
 * <p>
 * 账号表，平台内所有用户的账号、密码信息 服务类
 * </p>
 */
public interface IAccountService {

    Map<String, String> login(LoginFormDTO loginFormDTO);


    void logout();

    String refreshToken(String refreshToken);

    Map<String, String> register(RegisterFormDTO registerFormDTO);
}
