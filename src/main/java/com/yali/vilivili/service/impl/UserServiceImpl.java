package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.ro.AddUserRO;
import com.yali.vilivili.model.ro.UserSelectRO;
import com.yali.vilivili.model.ro.deleteByUserIdRO;
import com.yali.vilivili.model.ro.updateUserRO;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.service.FileUploadService;
import com.yali.vilivili.service.UserService;
import com.yali.vilivili.utils.AESUtil;
import com.yali.vilivili.utils.IpUtils;
import com.yali.vilivili.utils.MyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @Description 用户管理
 * @Date 2023.1.5 16:34
 * @Author fuqianlin
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;
    @Resource
    private HttpServletRequest request;

    @Resource
    private FileUploadService fileUploadService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public void addUser(AddUserRO ro) {
        // 设置用户信息头像，头像地址为当前服务器的static文件夹下的头像文件夹下的1.jpg
        String userAvatar = "/static/default_logo/1.png";
        String ipAddress = IpUtils.getIpAddress(request);
        //生成随机的用户账号num
        String num = UUID.randomUUID().toString().substring(0, 11);

        //判断用户邮箱是否存在
        UserEntity existingUser = userRepository.findByEmail(ro.getEmail());

        if (Objects.isNull(existingUser)) {
            UserEntity newUser = new UserEntity();
            newUser.setCreateTime(new Date());
            newUser.setUpdateTime(new Date());
            String password = AESUtil.encrypt(ro.getPassword());
            ro.setPassword(password);

            newUser.setUserAvatar(userAvatar);
            // 保存用户ip
            newUser.setUIp(ipAddress);

            newUser.setUsername(ro.getUsername());
            newUser.setPassword(ro.getPassword());
            newUser.setEmail(ro.getEmail());
            newUser.setUserNum(num);
            userRepository.save(newUser);
        } else {
            //邮箱已经存在，抛出异常或者返回错误信息
            throw new MyException(HttpStatus.OK.toString(), "邮箱已经注册");
        }
    }


    /**
     * 更新用户信息
     *
     * @param ro 更新用户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(updateUserRO ro) {

        String ipAddress = IpUtils.getIpAddress(request);

        try {
            userRepository.updateUserInfoByEmail(ro.getEmail(), ro.getUsername(), ro.getUserNum(), ro.getPhone(),
                    ro.getGender(), ro.getBirthdate(), ro.getSignature(), ro.getSchool(),
                    ro.getLocation(), ipAddress);
        } catch (Exception e) {
            throw new MyException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "更新用户信息失败");
        }

    }

    /**
     * 删除用户
     *
     * @param ro 删除用户
     */
    @Override

    @Transactional(rollbackFor = Exception.class)
    public void deleteById(deleteByUserIdRO ro) {
        Integer userId = ro.getId();
        byte isValid = ro.getIsValid();
        userRepository.deleteById(userId, isValid);

    }

    /**
     * 查询所有用户
     *
     * @param ro 查询条件
     */
    @Override

    public List<UserEntity> findUser(UserSelectRO ro) {
        try{
            List<UserEntity> findUser = userRepository.findAllUser(ro.getUsername(), ro.getIsValid(), ro.getType());
            //处理头像
            findUser.forEach(user -> {
                String avatarUrl = fileUploadService.getImageUrl(user.getUserAvatar());
                user.setUserAvatar(avatarUrl);
            });
            return findUser;
        }catch (Exception e){
            throw new MyException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "查询所有用户失败");
        }

    }

    /**
     * 分页查询用户列表
     *
     * @param page 页码
     * @param size 每页数量
     */
    @Override
    public List<UserEntity> findAllUserByPage(Integer page, Integer size) {
        try {
            //处理分页
            page = (page - 1) * size;
            List<UserEntity> allUserByPage = userRepository.findAllUserByPage(page, size);
            //处理头像
            allUserByPage.forEach(user -> {
                String avatarUrl = fileUploadService.getImageUrl(user.getUserAvatar());
                user.setUserAvatar(avatarUrl);
            });
            return userRepository.findAllUserByPage(page, size);
        } catch (Exception e) {
            throw new MyException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "分页查询用户列表失败");
        }

    }

    @Override
    public void attention(String username, String fansname) {
        redisTemplate.opsForSet().add(username+"粉丝",fansname);
        redisTemplate.opsForSet().add(fansname+"关注",username);
    }

    @Override
    public void cancel(String username, String fansname) {
        redisTemplate.opsForSet().remove(username+"关注",fansname);
        redisTemplate.opsForSet().remove(fansname+"粉丝",username);
    }
}
