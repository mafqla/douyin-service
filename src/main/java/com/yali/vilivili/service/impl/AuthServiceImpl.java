package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.ro.EmailRO;
import com.yali.vilivili.model.ro.LoginRO;
import com.yali.vilivili.model.ro.RegisterRO;
import com.yali.vilivili.model.ro.updateAndSaveUserRO;
import com.yali.vilivili.model.vo.LoginVO;
import com.yali.vilivili.model.vo.TokenInfoVO;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.service.AuthService;
import com.yali.vilivili.service.UserService;
import com.yali.vilivili.utils.AESUtil;
import com.yali.vilivili.utils.JwtUtils;
import com.yali.vilivili.utils.MyException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Date 2022/11/16 22:47
 * @Author pq
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    private UserRepository userRepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private JavaMailSender javaMailSender;

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
        // 将token存入redis中，设置过期时间为2小时）
        redisTemplate.opsForValue().set(loginUUID, token, 2, TimeUnit.HOURS);
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

    /**
     * 退出登录
     *
     */

    @Override
    public void logout(String token){
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
        // 调用javaMail发送邮件
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("sendmail_post@qq.com");
        message.setTo(ro.getEmail());
        message.setSubject("邮箱验证码");
        message.setText("<h1>邮箱验证码</h1><p>您的邮箱验证码为：" + code + "</p>"+ "<p>请在5分钟内使用</p>"
                + "<p>如果不是本人操作，请忽略</p>"+ "<p>此邮件由系统自动发出，请勿回复</p>");
        javaMailSender.send(message);
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

        updateAndSaveUserRO addUser = new updateAndSaveUserRO();
        addUser.setUsername(ro.getUsername());
        addUser.setPassword(ro.getPassword());
        addUser.setEmail(ro.getEmail());
        userService.updateAndSaveUser(addUser);
    }


}
