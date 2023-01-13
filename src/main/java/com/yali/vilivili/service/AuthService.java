package com.yali.vilivili.service;


import com.yali.vilivili.model.ro.LoginRO;
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


}
