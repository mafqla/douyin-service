package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.ro.LoginRO;
import com.yali.vilivili.model.vo.LoginVO;
import com.yali.vilivili.model.vo.TokenInfoVO;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.service.LoginService;
import com.yali.vilivili.utils.AESUtil;
import com.yali.vilivili.utils.JwtUtils;
import com.yali.vilivili.utils.MyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description
 * @Date 2022/11/16 22:47
 * @Author pq
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserRepository userRepository;

    @Override
    public LoginVO login(LoginRO ro) {
        UserEntity user=userRepository.findTopByUsername(ro.getUsername());
        if (Objects.isNull(user)){
            throw new MyException(HttpStatus.OK.toString(),"用户不存在！");
        }
        String pwd = AESUtil.encrypt(ro.getPassword());
        if (!StringUtils.equals(user.getPassword(), pwd)) {
            throw new MyException(HttpStatus.OK.toString(), "密码错误!");
        }

        if (user.getIsValid() != 0){
            throw new MyException(HttpStatus.FORBIDDEN.toString(),"用户已被禁用，请联系管理员");
        }
        TokenInfoVO tokenInfoVO = new TokenInfoVO();
        String loginUUID = UUID.randomUUID().toString();
        tokenInfoVO.setLoginUUID(loginUUID);
        tokenInfoVO.setUserId(user.getId());
        String token = JwtUtils.getToken(tokenInfoVO);
        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setEmail(user.getEmail());
        loginVO.setUserAvatar(user.getUserAvatar());
        loginVO.setType(String.valueOf(user.getType()));
        loginVO.setCreateTime(user.getCreateTime());
        loginVO.setToken(token);
        return loginVO;
    }
}
