package com.yali.vilivili.service.impl;

import com.yali.vilivili.constant.ErrorCode;
import com.yali.vilivili.model.User;
import com.yali.vilivili.model.ro.UserSelectRO;
import com.yali.vilivili.model.ro.updateAndSaveUserRO;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.service.CodeMessageService;
import com.yali.vilivili.service.UserService;
import com.yali.vilivili.utils.IpUtils;
import com.yali.vilivili.utils.MyException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Description 用户管理
 * @Date 2023.1.5 16:34
 * @Author fuqianlin
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private CodeMessageService codeMessageService;

    @Resource
    private UserRepository userRepository;
    @Resource
    private HttpServletRequest request;

    public void updateAndSaveUser(updateAndSaveUserRO ro) {

        // 设置用户信息头像，头像地址为当前服务器的static文件夹下的头像文件夹下的1.jpg
        String userAvatar = "http://127.0.0.1:8080/static/avatar/1.jpg";
        String ipAddress = IpUtils.getIpAddress(request);
        String userPwd = PssEncryptionUtils.encryptMD5(passwordSalt, ro.getPassword());
        ro.setPassword(userPwd);

        User saveUser = new User();
        //判断用户邮箱是否存在
        User isEmail = userRepository.findByEmail(ro.getEmail());
        if (Objects.nonNull(isEmail)) {
            throw new MyException(HttpStatus.OK.value(), ErrorCode.USER_EXISTED, codeMessageService.message(ErrorCode.USER_EXISTED));
        }
        saveUser.setUserAvatar(userAvatar);
        // 保存用户ip
        saveUser.setUIp(ipAddress);
        BeanUtils.copyProperties(ro, saveUser);
        userRepository.save(saveUser);
    }

    public List<User> findAllUser(UserSelectRO ro){
        return userRepository.findAllUser(ro.getUsername(), ro.getIsValid(), ro.getType());
    }
}
