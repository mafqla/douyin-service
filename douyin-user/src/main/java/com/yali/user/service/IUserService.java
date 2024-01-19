package com.yali.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.api.dto.user.LoginFormDTO;
import com.yali.api.dto.user.RegisterFormDTO;
import com.yali.api.dto.user.UserDTO;
import com.yali.common.domain.dto.LoginUserDTO;
import com.yali.user.domain.dto.UserFormDTO;
import com.yali.user.domain.po.User;
import com.yali.user.domain.vo.UserDetailVO;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 */
public interface IUserService extends IService<User> {
    LoginUserDTO queryUserDetail(LoginFormDTO loginDTO);

    void resetPassword(Long userId);

    UserDetailVO myInfo();

    Map<String, Long> addUserByPhone(RegisterFormDTO registerFormDTO);

    void updatePasswordByPhone(String cellPhone, String code, String password);

    Long saveUser(UserDTO userDTO);

    void updateUser(UserDTO userDTO);

    void updateUserWithPassword(UserFormDTO userDTO);
}
