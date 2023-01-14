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

    void sendEmailCode(EmailRO ro);

    /**
     * 注册
     * @param ro 注册参数
     */
    void register(RegisterRO ro);
}
