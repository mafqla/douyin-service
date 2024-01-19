package com.yali.auth.service.impl;

import com.yali.api.client.user.UserClient;
import com.yali.api.dto.user.LoginFormDTO;
import com.yali.api.dto.user.RegisterFormDTO;
import com.yali.auth.common.constants.JwtConstants;
import com.yali.auth.domain.po.LoginRecord;
import com.yali.auth.service.IAccountService;
import com.yali.auth.service.ILoginRecordService;
import com.yali.auth.util.JwtTool;
import com.yali.common.domain.dto.LoginUserDTO;
import com.yali.common.exceptions.BadRequestException;
import com.yali.common.utils.BooleanUtils;
import com.yali.common.utils.UserContext;
import com.yali.common.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 账号表，平台内所有用户的账号、密码信息 服务实现类
 * </p>
 */
@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AccountServiceImpl implements IAccountService {
    private final JwtTool jwtTool;
    private final UserClient userClient;
    private final ILoginRecordService loginRecordService;

    @Override
    public Map<String, String> login(LoginFormDTO loginDTO) {

        LocalDateTime loginStartTime = LocalDateTime.now();
        LoginUserDTO detail = null;
        Duration loginDuration = null;
        try {
            // 1.查询并校验用户信息
            detail = userClient.queryUserDetail(loginDTO);
            if (detail == null) {
                throw new BadRequestException("登录信息有误");
            }

            // 2.基于JWT生成登录token
            // 2.1.设置记住我标记
            detail.setRememberMe(loginDTO.getRememberMe());

            // 2.2.生成token
            String token = generateToken(detail);


            // 4.返回结果
            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            LocalDateTime loginEndTime = LocalDateTime.now();
            // 计算登录耗时
            loginDuration = Duration.between(loginStartTime, loginEndTime);
            // 4.计入登录信息表
            loginRecordService.loginSuccess(loginDTO.getCellPhone(), detail.getUserId(), loginDuration, LoginRecord.Status.SUCCESS.getValue());
            return response;
        } catch (Exception e) {
            log.error("登录失败", e);
            // 4. 计入登录信息表 - 失败
            Integer status = LoginRecord.Status.FAILURE.getValue();
            loginRecordService.loginFail(loginDTO.getCellPhone(), detail == null ? null : detail.getUserId(), loginDuration, status, e.getMessage());
            throw new BadRequestException("登录失败");
        }
    }


    private String generateToken(LoginUserDTO detail) {
        // 2.2.生成access-token
        String token = jwtTool.createToken(detail);
        // 2.3.生成refresh-token，将refresh-token的JTI 保存到Redis
        String refreshToken = jwtTool.createRefreshToken(detail);
        // 2.4.将refresh-token写入用户cookie，并设置HttpOnly为true
        int maxAge = BooleanUtils.isTrue(detail.getRememberMe()) ?
                (int) JwtConstants.JWT_REMEMBER_ME_TTL.toSeconds() : -1;
        WebUtils.cookieBuilder()
                .name(detail.getRoleId() == 2 ? JwtConstants.REFRESH_HEADER : JwtConstants.ADMIN_REFRESH_HEADER)
                .value(refreshToken)
                .maxAge(maxAge)
                .httpOnly(true)
                .build();
        return token;
    }

    @Override
    public void logout() {
        // 获取登录用户id
        Long userId = UserContext.getUser();
        // 删除jti
        jwtTool.cleanJtiCache();
        // 删除cookie
        WebUtils.cookieBuilder()
                .name(JwtConstants.REFRESH_HEADER)
                .value("")
                .maxAge(0)
                .httpOnly(true)
                .build();
        loginRecordService.logout(userId);
    }

    @Override
    public String refreshToken(String refreshToken) {
        // 1.校验refresh-token,校验JTI
        LoginUserDTO userDTO = jwtTool.parseRefreshToken(refreshToken);
        // 2.生成新的access-token、refresh-token
        return generateToken(userDTO);
    }

    @Override
    public Map<String, String> register(RegisterFormDTO registerFormDTO) {
        LocalDateTime loginStartTime = LocalDateTime.now();

        Duration loginDuration = null;
        Long userId = null;
        try {

            Map<String, Long> result = userClient.registerByPhone(registerFormDTO);
            userId = result.get("userId");
            Long roleId = result.get("roleId");
            if (userId == null) {
                throw new BadRequestException("注册失败");
            }

            // 2.基于JWT生成登录token
            LoginUserDTO loginUserDTO = new LoginUserDTO();
            loginUserDTO.setUserId(userId);
            loginUserDTO.setRoleId(roleId);
            // 2.1.生成token
            String token = generateToken(loginUserDTO);


            // 3.返回结果
            Map<String, String> response = new HashMap<>();
            response.put("token", token);

            LocalDateTime loginEndTime = LocalDateTime.now();
            // 计算登录耗时
            loginDuration = Duration.between(loginStartTime, loginEndTime);
            // 4.计入登录信息表
            loginRecordService.loginSuccess(registerFormDTO.getCellPhone(), userId, loginDuration, LoginRecord.Status.SUCCESS.getValue());
            return response;
        } catch (Exception e) {
            log.error("登录失败", e);
            // 4. 计入登录信息表 - 失败
            Integer status = LoginRecord.Status.FAILURE.getValue();
            loginRecordService.loginFail(registerFormDTO.getCellPhone(), userId, loginDuration, status, e.getMessage());
            throw new BadRequestException("登录失败");
        }
    }
}
