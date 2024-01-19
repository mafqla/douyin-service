package com.yali.vilivili.service.impl;

import com.yali.vilivili.config.UserTypeConfig;
import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.ro.AddUserRO;
import com.yali.vilivili.model.ro.UserSelectRO;
import com.yali.vilivili.model.ro.deleteByUserIdRO;
import com.yali.vilivili.model.ro.updateUserRO;
import com.yali.vilivili.model.vo.FileUploadVO;
import com.yali.vilivili.model.vo.UserInfoVO;
import com.yali.vilivili.repository.CollectRepository;
import com.yali.vilivili.repository.LikeRepository;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.repository.VideosInfoRepository;
import com.yali.vilivili.service.AttentionService;
import com.yali.vilivili.service.FileUploadService;
import com.yali.vilivili.service.LikeService;
import com.yali.vilivili.service.UserService;
import com.yali.vilivili.utils.AESUtil;
import com.yali.vilivili.utils.IpUtils;
import com.yali.vilivili.utils.MyException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.yali.vilivili.utils.IpUtils.getIpSource;

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
    private RedisTemplate<String, String> redisTemplate;


    @Resource
    private LikeRepository likeRepository;

    @Resource
    private VideosInfoRepository videosInfoRepository;

    @Resource
    private CollectRepository collectRepository;

    @Resource
    private AttentionService attentionService;


    @Override
    public void addUser(AddUserRO ro) {
        // 设置用户信息头像，头像地址为当前服务器的static文件夹下的头像文件夹下的1.jpg
        String userAvatar = "/static/default_logo/1.png";
        String ipAddress = IpUtils.getIpAddress(request);
        //生成随机的用户账号num
        //String num = UUID.randomUUID().toString().substring(0, 11);
        Random random = new Random();
        int num = random.nextInt(11);
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
            newUser.setUserNum(String.valueOf(num));
            userRepository.save(newUser);
        } else {
            //邮箱已经存在，抛出异常或者返回错误信息
            throw new MyException(String.valueOf(HttpStatus.OK.value()), "邮箱已经注册");
        }
    }


    /**
     * 更新用户信息
     *
     * @param ro 更新用户
     * @return
     */
    @Override
    public void updateUser(updateUserRO ro, MultipartFile avatar) {


        try {
            UserEntity user = userRepository.findUserById(ro.getId());
            String ipAddress = IpUtils.getIpAddress(request);
            if (avatar != null) {
                // 处理头像
                FileUploadVO avatarAddress = fileUploadService.imageUpload(avatar);
                user.setUserAvatar(avatarAddress.getPath());
            }

            if (Objects.nonNull(user)) {
                user.setUIp(ipAddress);

                user.setUpdateTime(new Date());
                // 使用Optional来处理字段的选择性更新
                Optional.ofNullable(ro.getUsername()).ifPresent(user::setUsername);
                Optional.ofNullable(ro.getGender()).ifPresent(user::setGender);
                Optional.ofNullable(ro.getBirthdate()).ifPresent(user::setBirthdate);
                Optional.ofNullable(ro.getSchool()).ifPresent(user::setSchool);
                Optional.ofNullable(ro.getSignature()).ifPresent(user::setSignature);
                Optional.ofNullable(ro.getLocation()).ifPresent(user::setLocation);
                Optional.ofNullable(ro.getPhone()).ifPresent(user::setPhone);
                Optional.ofNullable(ro.getUserNum()).ifPresent(user::setUserNum);
                userRepository.save(user);
            } else {
                throw new MyException(String.valueOf(HttpStatus.NOT_FOUND.value()), "用户不存在");
            }

        } catch (Exception e) {
            throw new MyException(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), "更新用户信息失败");
        }


    }

    /**
     * 查询用户信息
     *
     * @param currentUserId  用户id
     * @param userIdToLookup 要查询的用户id 可以为空
     */
    @Resource
    private LikeService likeService;

    @Override
    public UserInfoVO findUserById(Integer currentUserId, Integer userIdToLookup) {
        try {


            Integer id = userIdToLookup != null ? userIdToLookup : currentUserId;
            // 根据 用户id 统计点赞数、收藏数、发布数
            long LikeCount = likeRepository.countbyUserId(id);
            long PublishCount = videosInfoRepository.countbyUserId(id);
            long CollectCount = collectRepository.countbyUserId(id);


            //粉丝数量
            long FansCount = attentionService.findFansCount(id);

            // 关注数量
            long AttentionCount = attentionService.findAttentionCount(id);
            // 获赞数量
            long ReceivedLikeCount = likeService.getUserReceivedLikeCount(id);
            UserEntity user = userRepository.findUserById(id);
            // 处理头像，如果需要的话
            String avatarUrl = fileUploadService.getImageUrl(user.getUserAvatar());
            // 用户类型（0：普通用户，1：管理员，2：超级管理员，3：测试用户)
            String userType = UserTypeConfig.mapUserType(user.getType());
            //处理ip
            String ipAddress = getIpSource(user.getUIp());
            UserInfoVO userInfoVO = new UserInfoVO();

            Integer isFollow = attentionService.isAttention(currentUserId, id);
            userInfoVO.setIsAttention(isFollow);

            userInfoVO.setId(user.getId());
            userInfoVO.setUsername(user.getUsername());
            userInfoVO.setAvatar(avatarUrl);
            userInfoVO.setUserNum(user.getUserNum());
            userInfoVO.setAddress(user.getLocation());
            userInfoVO.setBirthday(user.getBirthdate());
            userInfoVO.setGender(user.getGender());
            userInfoVO.setUserSignature(user.getSignature());
            userInfoVO.setSchool(user.getSchool());
            userInfoVO.setIpAddress(ipAddress);
            userInfoVO.setCollectVideosCount(CollectCount);
            userInfoVO.setLikeVideosCount(LikeCount);
            userInfoVO.setUploadVideosCount(PublishCount);
            userInfoVO.setAttentionCount(AttentionCount);
            userInfoVO.setFansCount(FansCount);
            userInfoVO.setLikeCount(ReceivedLikeCount);
            userInfoVO.setUserType(userType);
            userInfoVO.setUserAuth(user.getUserAuth());
            userInfoVO.setUserAuthType(user.getUserAuthType());

            return userInfoVO;
        } catch (Exception e) {
            throw new MyException("203", "查询信息失败");
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
        try {
            List<UserEntity> findUser = userRepository.findAllUser(ro.getUsername(), ro.getIsValid(), ro.getType());
            //处理头像
            findUser.forEach(user -> {
                String avatarUrl = fileUploadService.getImageUrl(user.getUserAvatar());
                user.setUserAvatar(avatarUrl);
            });
            return findUser;
        } catch (Exception e) {
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

}
