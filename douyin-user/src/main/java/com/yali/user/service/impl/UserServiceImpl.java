package com.yali.user.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yali.api.dto.user.LoginFormDTO;
import com.yali.api.dto.user.RegisterFormDTO;
import com.yali.api.dto.user.UserDTO;
import com.yali.common.domain.dto.LoginUserDTO;
import com.yali.common.exceptions.BadRequestException;
import com.yali.common.exceptions.ForbiddenException;
import com.yali.common.exceptions.UnauthorizedException;
import com.yali.common.utils.*;
import com.yali.user.domain.dto.UserFormDTO;
import com.yali.user.domain.po.User;
import com.yali.user.domain.po.UserDetail;
import com.yali.user.domain.vo.UserDetailVO;
import com.yali.user.enums.UserStatus;
import com.yali.user.mapper.UserMapper;
import com.yali.user.service.ICodeService;
import com.yali.user.service.IUserDetailService;
import com.yali.user.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static com.yali.user.constants.UserConstants.DEFAULT_PASSWORD;
import static com.yali.user.constants.UserErrorInfo.Msg.*;


/**
 * <p>
 * 用户表 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    private final PasswordEncoder passwordEncoder;
    private final ICodeService codeService;
    private final IUserDetailService detailService;


    @Override
    public LoginUserDTO queryUserDetail(LoginFormDTO loginDTO) {
        // 1.判断登录方式
        Integer type = loginDTO.getType();
        User user = null;
        // 2.用户名和密码登录
        if (type == 1) {
            user = loginByPw(loginDTO);
        }
        // 3.验证码登录
        if (type == 2) {
            user = loginByVerifyCode(loginDTO.getCellPhone(), loginDTO.getPassword());
        }
        // 4.错误的登录方式
        if (user == null) {
            throw new BadRequestException(ILLEGAL_LOGIN_TYPE);
        }
        // 6.封装返回
        LoginUserDTO userDTO = new LoginUserDTO();
        UserDetail detail = detailService.getById(user.getId());
        Long roleId = detail.getRoleId();
        // 5.存储ipv4、ipv6地址
        String ipv4 = WebUtils.getRemoteAddr();
        detailService.updateIpById(user.getId(), ipv4);
        // 6.存储到userDetail
        userDTO.setUserId(user.getId());
        userDTO.setRoleId(roleId);
        return userDTO;
    }

    @Override
    public void resetPassword(Long userId) {
        User user = new User();
        user.setId(userId);
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        AssertUtils.isTrue(updateById(user), USER_ID_NOT_EXISTS);
    }

    @Override
    public UserDetailVO myInfo() {
        // 1.获取登录用户id
        Long userId = UserContext.getUser();
        if (userId == null) {
            return null;
        }
        // 2.查询用户
        UserDetail userDetail = detailService.queryById(userId);
        AssertUtils.isNotNull(userDetail, USER_ID_NOT_EXISTS);
        // 3.封装vo
        // 3.1.基本信息
        UserDetailVO vo = BeanUtils.copyBean(userDetail, UserDetailVO.class);
        vo.setUid(userDetail.getId());
        vo.setAvatar_thumb(userDetail.getAvatarThumb());
        vo.setCover_url(userDetail.getCoverUrl());
        vo.setUnique_id(userDetail.getUniqueId());
        vo.setCustom_verify(userDetail.getCustomVerify());
        vo.setVerification_type(userDetail.getVerificationType());
        vo.setIp_location(WebUtils.getIpSource(userDetail.getIpv4()));
        return vo;
    }

    @Override
    public Map<String, Long> addUserByPhone(RegisterFormDTO registerFormDTO) {
        User user = new User();
        // 1.验证码校验
        codeService.verifyCode(registerFormDTO.getCellPhone(), registerFormDTO.getVerifyCode());
        // 2.判断手机号是否存在
        int count = Math.toIntExact(lambdaQuery().eq(User::getCellPhone, registerFormDTO.getCellPhone()).count());
        if (count > 0) {
            throw new BadRequestException(PHONE_ALREADY_EXISTS);
        }


        // 3.加密密码
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        // 4.新增
        user.setCellPhone(registerFormDTO.getCellPhone());

        // 生成UUID并取其前11位作为昵称
        String uuidForNickname = IdUtil.simpleUUID();
        user.setNickname("用户" + uuidForNickname);

        // 生成另一个UUID并取其前11位作为唯一ID
        String uuidForUniqueId = IdUtil.simpleUUID();
        user.setUniqueId(uuidForUniqueId);

        save(user);

        // 2.新增详情
        UserDetail detail = new UserDetail();
        detail.setSignature("该用户很懒，什么都没留下");
        detail.setId(user.getId());
        detail.setRoleId(0L);
        detail.setVerificationType(0);
        // 3.设置默认背景图
        detail.setCoverUrl("default-bg.jpg");
        detail.setAvatarThumb("default-icon.jpg");
        detailService.save(detail);
        Map<String, Long> result = new HashMap<>();
        result.put("userId", user.getId());
        result.put("roleId", detail.getRoleId());

        return result;
    }

    @Override
    public void updatePasswordByPhone(String cellPhone, String code, String password) {
        // 1.验证码校验
        codeService.verifyCode(cellPhone, code);
        // 2.查询用户
        User oldUser = lambdaQuery().eq(User::getCellPhone, cellPhone).one();
        if (oldUser == null) {
            // 手机号不存在
            throw new BadRequestException(PHONE_NOT_EXISTS);
        }
        // 2.修改密码
        User user = new User();
        user.setId(user.getId());
        user.setPassword(passwordEncoder.encode(password));
        updateById(user);
    }

    public void updatePhoneById(Long id, String cellPhone) {
        // 1.1.判断是否需要修改手机号
        if (StringUtils.isNotBlank(cellPhone)) {
            // 1.2.需要修改，封装数据
            User user = new User();
            user.setId(id);
            user.setCellPhone(cellPhone);
            // 1.3.修改
            updateById(user);
        }
    }

    @Override
    @Transactional
    public Long saveUser(UserDTO userDTO) {
        // 1.保存用户基本信息
        User user = new User();
        user.setPassword(passwordEncoder.encode(DEFAULT_PASSWORD));
        user.setCellPhone(userDTO.getCellPhone());
        user.setNickname(userDTO.getCellPhone());
        save(user);
        // 2.新增详情
        UserDetail detail = BeanUtils.toBean(userDTO, UserDetail.class);
        detail.setId(user.getId());
        detailService.save(detail);
        return user.getId();
    }

    @Override
    @Transactional
    public void updateUser(UserDTO userDTO) {
        // 1.如果传递了手机号，则修改手机号
        String cellphone = userDTO.getCellPhone();
        if (StringUtils.isNotBlank(cellphone)) {
            User user = new User();
            user.setId(userDTO.getUid());
            user.setCellPhone(cellphone);
            updateById(user);
        }
        // 2.修改详情
        UserDetail detail = BeanUtils.toBean(userDTO, UserDetail.class);
        detail.setVerificationType(null);
        detailService.updateById(detail);
    }

    @Override
    public void updateUserWithPassword(UserFormDTO userDTO) {
        // 1.尝试更新密码
        String pw = userDTO.getPassword();
        String oldPw = userDTO.getOldPassword();
        if (StringUtils.isNotBlank(pw) && StringUtils.isNotBlank(pw)) {
            Long userId = UserContext.getUser();
            // 1.1.查询用户
            User user = getById(userId);
            // 1.2.校验
            if (user == null) {
                throw new UnauthorizedException(USER_ID_NOT_EXISTS);
            }
            // 1.3.校验密码
            if (!passwordEncoder.matches(oldPw, user.getPassword())) {
                // 密码不一致
                throw new UnauthorizedException(INVALID_UN_OR_PW);
            }
            // 1.4.修改密码
            user = new User();
            user.setId(userId);
            user.setPassword(passwordEncoder.encode(pw));
            updateById(user);
        }
        // 2.更新用户详情
        UserDetail detail = BeanUtils.toBean(userDTO, UserDetail.class);
        detail.setRoleId(null);
        detail.setVerificationType(null);
        detailService.updateById(detail);
    }

    public User loginByPw(LoginFormDTO loginDTO) {
        // 1.数据校验
        String cellPhone = loginDTO.getCellPhone();
        if (StrUtil.isBlank(cellPhone)) {
            throw new BadRequestException(INVALID_UN);
        }
        // 2.根据用户名或手机号查询
        User user = lambdaQuery()
                .eq(StrUtil.isNotBlank(cellPhone), User::getCellPhone, cellPhone)
                .one();
        AssertUtils.isNotNull(user, INVALID_UN_OR_PW);
        // 3.校验是否禁用
        if (user.getStatus() == UserStatus.FROZEN) {
            throw new ForbiddenException(USER_FROZEN);
        }
        // 4.校验密码
        if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadRequestException(INVALID_UN_OR_PW);
        }

        return user;
    }


    public User loginByVerifyCode(String phone, String code) {
        // 1.校验验证码
        codeService.verifyCode(phone, code);
        // 2.根据手机号查询
        User user = lambdaQuery().eq(User::getCellPhone, phone).one();
        if (user == null) {
            throw new BadRequestException(PHONE_NOT_EXISTS);
        }
        // 3.校验是否禁用
        if (user.getStatus() == UserStatus.FROZEN) {
            throw new ForbiddenException(USER_FROZEN);
        }
        return user;
    }
}
