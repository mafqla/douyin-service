package com.yali.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.auth.domain.po.LoginRecord;

import java.time.Duration;

/**
 * <p>
 * 登录信息记录表 服务类
 * </p>
 */
public interface ILoginRecordService extends IService<LoginRecord> {

    void saveAsync(LoginRecord record);

    void updateLogoutTimeAsync(LoginRecord record);

    void loginSuccess(String cellphone, Long userId, Duration expireTime, Integer status);

    void loginFail(String cellphone, Long userId, Duration expireTime, Integer status, String message);

    void logout(Long userId);
}
