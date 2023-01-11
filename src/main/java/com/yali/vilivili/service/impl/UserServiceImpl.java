package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.ro.UserSelectRO;
import com.yali.vilivili.model.ro.deleteByUserIdRO;
import com.yali.vilivili.model.ro.updateAndSaveUserRO;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.service.CodeMessageService;
import com.yali.vilivili.service.UserService;
import com.yali.vilivili.utils.AESUtil;
import com.yali.vilivili.utils.IpUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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

    /**
     * 更新和保存用户
     *
     * @param ro
     */
    public void updateAndSaveUser(updateAndSaveUserRO ro) {

        // 设置用户信息头像，头像地址为当前服务器的static文件夹下的头像文件夹下的1.jpg
        String userAvatar = "http://127.0.0.1:8080/static/default_logo/1.png";
        String ipAddress = IpUtils.getIpAddress(request);

        String password= AESUtil.encrypt(ro.getPassword());
        ro.setPassword(password);
        //判断用户邮箱是否存在
        UserEntity isEmail = userRepository.findByEmail(ro.getEmail());
        if (Objects.nonNull(isEmail)) {
            isEmail.setUpdateTime(new Date());
            isEmail.setUIp(ipAddress);
            isEmail.setUserAvatar(userAvatar);
            isEmail.setUsername(ro.getUsername());
            isEmail.setPassword(ro.getPassword());
            isEmail.setEmail(ro.getEmail());
            isEmail.setIsValid(ro.getIsValid());
            isEmail.setType(ro.getType());
            userRepository.save(isEmail);
        } else {
            ro.setCreateTime(new Date());
            ro.setUpdateTime(new Date());
            UserEntity saveUser = new UserEntity();
            saveUser.setUserAvatar(userAvatar);
            // 保存用户ip
            saveUser.setUIp(ipAddress);
            BeanUtils.copyProperties(ro, saveUser);
            System.out.println(saveUser);
            userRepository.save(saveUser);
        }
    }

    /**
     * 删除用户
     *
     * @param ro
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(deleteByUserIdRO ro) {
        Integer userId = ro.getId();
        byte isValid = ro.getIsValid();
        userRepository.deleteById(userId, isValid);

    }

    /**
     * 查询所有用户
     *
     * @param ro
     * @return
     */
    public List<UserEntity> findAllUser(UserSelectRO ro){
        return userRepository.findAllUser(ro.getUsername(), ro.getIsValid(), ro.getType());
    }
}
