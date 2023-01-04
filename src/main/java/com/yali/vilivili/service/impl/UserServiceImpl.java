package com.yali.vilivili.service.impl;

import com.yali.vilivili.constant.ErrorCode;
import com.yali.vilivili.model.User;
import com.yali.vilivili.model.ro.UserAddRO;
import com.yali.vilivili.model.ro.UserSelectRO;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.service.CodeMessageService;
import com.yali.vilivili.service.UserService;
import com.yali.vilivili.utils.MyException;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Description 用户管理
 * @Date 2023/1/3 21:34
 * @Author pq
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private CodeMessageService codeMessageService;

    @Resource
    private UserRepository userRepository;

    public void saveUser(UserAddRO ro) {
        User user = userRepository.findTopByUsername(ro.getUsername());
        if (Objects.nonNull(user)) {
            throw new MyException(HttpStatus.OK.value(), ErrorCode.USER_EXISTED, codeMessageService.message(ErrorCode.USER_EXISTED));
        }
        User saveUser = new User();
        BeanUtils.copyProperties(ro, saveUser);
        userRepository.save(saveUser);
    }

    public List<User> findAllUser(UserSelectRO ro){
        return userRepository.findAllUser(ro.getUsername(), ro.getIsValid(), ro.getType());
    }
}
