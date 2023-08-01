package com.yali.vilivili.service.impl;

import com.yali.vilivili.constant.ErrorCode;
import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.ro.AddUserRO;
import com.yali.vilivili.model.ro.EmailRO;
import com.yali.vilivili.model.ro.LoginRO;
import com.yali.vilivili.model.ro.RegisterRO;
import com.yali.vilivili.model.vo.LoginVO;
import com.yali.vilivili.model.vo.TokenInfoVO;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.service.AuthService;
import com.yali.vilivili.service.UserService;
import com.yali.vilivili.utils.AESUtil;
import com.yali.vilivili.utils.JwtUtils;
import com.yali.vilivili.utils.MyException;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.yali.vilivili.utils.IpUtils.getIpSource;

/**
 * @Description
 * @Date 2022/11/16 22:47
 * @Author fuqianlin
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Resource
    private UserService userService;

    @Resource
    private JavaMailSender javaMailSender;

    @Resource
    private FileUploadServiceImpl fileUploadService;

    @Override
    public LoginVO login(LoginRO ro) {
        UserEntity user = userRepository.findTopByEmail(ro.getEmail());
        if (Objects.isNull(user)) {
            throw new MyException("1001", ErrorCode.USER_NOT_FOUND.getMessage());
        }
        String pwd = AESUtil.encrypt(ro.getPassword());
        if (!StringUtils.equals(user.getPassword(), pwd)) {
            throw new MyException(HttpStatus.OK.toString(), "密码错误!");
        }

        if (user.getIsValid() != 0) {
            throw new MyException(HttpStatus.FORBIDDEN.toString(), "用户已被禁用，请联系管理员");
        }
        return getLoginVO(user);
    }

    /**
     * 退出登录
     */

    @Override
    public void logout(String token) {
        TokenInfoVO tokenInfoVO = JwtUtils.decodeJwt(token);
        redisTemplate.delete(tokenInfoVO.getLoginUUID());
    }

    /**
     * 使用邮箱发送验证码
     */
    @Override
    public void sendEmailCode(EmailRO ro) {
        // 生成验证码
        String code = UUID.randomUUID().toString().substring(0, 6);
        // 将验证码存入redis中，设置过期时间为5分钟
        redisTemplate.opsForValue().set(ro.getEmail(), code, 5, TimeUnit.MINUTES);
        try {
            // 调用javaMail发送邮件
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("sendmail_post@qq.com");
            message.setTo(ro.getEmail());
            message.setSubject("账户注册验证码");
            message.setText("<h1>邮箱验证码</h1><p>您的邮箱验证码为：" + code + "</p>" + "<p>请在5分钟内使用</p>"
                    + "<p>如果不是本人操作，请忽略</p>" + "<p>此邮件由系统自动发出，请勿回复</p>");
            javaMailSender.send(message);
            System.out.println("邮件发送成功,验证码为:" + code);
        } catch (Exception e) {
            throw new MyException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "邮件发送失败");
        }
    }

    /**
     * 注册用户
     */
    @Override
    public void register(RegisterRO ro) {

        // 判断密码是否一致
        if (!StringUtils.equals(ro.getPassword(), ro.getConfirmPassword())) {
            throw new MyException(HttpStatus.OK.toString(), "两次密码不一致!");
        }
        // 判断验证码是否正确
        String code = redisTemplate.opsForValue().get(ro.getEmail());
        if (!StringUtils.equals(code, ro.getCode())) {
            throw new MyException(HttpStatus.OK.toString(), "验证码错误!");
        }

        AddUserRO addUser = new AddUserRO();
        addUser.setUsername(ro.getUsername());
        addUser.setPassword(ro.getPassword());
        addUser.setEmail(ro.getEmail());

//        userService.updateAndSaveUser(addUser);
        userService.addUser(addUser);
    }

    /**
     * 重置密码
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(String email, String password, String code) {
        UserEntity user = userRepository.findByEmail(email);
        if (Objects.isNull(user)) {
            throw new MyException(HttpStatus.OK.toString(), "用户不存在!");
        }
        // 判断验证码是否正确
        String verifyCode = redisTemplate.opsForValue().get(email);
        if (!StringUtils.equals(verifyCode, code)) {
            throw new MyException(HttpStatus.OK.toString(), "验证码错误!");
        }
        // 重置密码
        userRepository.resetPasswordByEmail(email, AESUtil.encrypt(password));
    }

    /**
     * 根据用户id修改头像
     *
     * @param email  用户邮箱
     * @param avatar 头像
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateAvatar(String email, String avatar) {
        UserEntity user = userRepository.findByEmail(email);
        if (Objects.isNull(user)) {
            throw new MyException(HttpStatus.OK.toString(), "用户不存在!");
        }
        userRepository.updateAvatarByEmail(email, avatar);

    }

    @Override
    public LoginVO emailLogin(String email, String code) {
        String redisCode = redisTemplate.opsForValue().get(email);


        if (StringUtils.equals(code, redisCode)) {
            AddUserRO addUserRO = new AddUserRO();
            addUserRO.setEmail(email);
            Random random = new Random();
            addUserRO.setUsername("用户" + random.nextInt(10));
            addUserRO.setPassword(AESUtil.encrypt("123456"));
            UserEntity user = userRepository.findTopByEmail(email);

            try {
                userService.addUser(addUserRO);
            } catch (MyException e) {

                return getLoginVO(user);
            }

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("sendmail_post@qq.com");
            message.setTo(email);
            message.setSubject("账户注册成功");
            message.setText("邮箱密码为:123456");
            javaMailSender.send(message);


            return getLoginVO(user);


        } else {
            throw new MyException(HttpStatus.OK.toString(), "验证码错误!");
        }
    }

    @NotNull
    private LoginVO getLoginVO(UserEntity user) {
        TokenInfoVO tokenInfoVO = new TokenInfoVO();
        String loginUUID = UUID.randomUUID().toString();
        tokenInfoVO.setLoginUUID(loginUUID);
        tokenInfoVO.setUserId(user.getId());
        String token = JwtUtils.getToken(tokenInfoVO);
        // 将token存入redis中，设置过期时间为2小时）
        redisTemplate.opsForValue().set(loginUUID, token, 2, TimeUnit.HOURS);

        // 获取用户头像url
        String avatarUrl = fileUploadService.getImageUrl(user.getUserAvatar());

        //获取ip属地
        String ipLocation = getIpSource(user.getUIp());

        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setEmail(user.getEmail());
        loginVO.setUserAvatar(avatarUrl);
        loginVO.setType(String.valueOf(user.getType()));
        loginVO.setCreateTime(user.getCreateTime());
        loginVO.setPhone(user.getPhone());
        loginVO.setUserNum(user.getUserNum());
        loginVO.setIpLocation(ipLocation);
        loginVO.setBirthdate(user.getBirthdate());
        loginVO.setGender(user.getGender());
        loginVO.setSignature(user.getSignature());
        loginVO.setSchool(user.getSchool());
        loginVO.setLocation(user.getLocation());
        loginVO.setToken(token);
        return loginVO;
    }


}
