package com.yali.vilivili.service;


import com.yali.vilivili.model.ro.EmailRO;
import com.yali.vilivili.model.ro.LoginRO;
import com.yali.vilivili.model.ro.RegisterRO;
import com.yali.vilivili.model.vo.LoginVO;

/**
 * @Description
 * @Date 2022/11/16 22:46
 * @Author pq
 */
public interface AuthService {

    /**
     *  登录接口
     * @param ro 登录参数
     * @return LoginVO
     */
    LoginVO login(LoginRO ro);


    /**
     * 退出登录
     * @param token token
     */
    void logout(String token);

    /**
     * 发送验证码
     * @param ro 邮箱参数
     */
    void sendEmailCode(EmailRO ro);

    /**
     * 注册
     * @param ro 注册参数
     */
    void register(RegisterRO ro);

    /**
     * 重置密码
     * @param email 用户邮箱
     * @param password 新密码
     * @param code 验证码
     */
    void resetPassword(String email,  String password, String code);

    /**
     * 根据用户id修改头像
     * @param email 用户邮箱
     * @param avatar 头像
     */
    void updateAvatar(String email, String avatar);

}
