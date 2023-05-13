package com.yali.vilivili.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.mapper.*;
import com.yali.vilivili.model.entity.*;
import com.yali.vilivili.model.ro.AddUserRO;
import com.yali.vilivili.model.ro.EmailRO;
import com.yali.vilivili.model.ro.LoginRO;
import com.yali.vilivili.model.ro.RegisterRO;
import com.yali.vilivili.model.vo.LoginVO;
import com.yali.vilivili.model.vo.VideosEntityVO;
import com.yali.vilivili.service.AuthService;
import com.yali.vilivili.utils.HostHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @Description 登录注册接口
 * @Date 2022/11/16 22:45
 * @Author pq
 */
@RestController
@RequestMapping("/auth")
@Api(value = "账号相关", tags = {"账户相关"})
public class AuthController extends BaseController {
    @Resource
    private AuthService authService;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private VideosEntityMapper videosEntityMapper;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private UserEntityMapper userEntityMapper;

    @Resource
    private CollectionMapper collectionMapper;

    @Resource
    private VideosInfoEntityMapper videosInfoEntityMapper;

    @Resource
    CommentMapper commentMapper;

    @Resource
    VideosTagMapper videosTagMapper;

    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ResponseEntity<OR<LoginVO>> login(@Validated LoginRO ro, BindingResult br) {
        return this.processData(() -> authService.login(ro), br, "登录成功", this::processException);
    }

    @ApiOperation(value = "退出登录")
    @PostMapping("/logout")
    public ResponseEntity<OR<Void>> logout(HttpServletRequest request) {
        String token = request.getHeader("loginToken");
        authService.logout(token);
        return process(this::successResult);
    }

    @ApiOperation(value = "发送验证码")
    @PostMapping("/sendCode")
    public ResponseEntity<OR<Void>> sendCode(@Valid EmailRO ro) {
        authService.sendEmailCode(ro);
        return process(this::successResult);
    }

    @ApiOperation(value = "注册")
    @PostMapping("/register")
    public ResponseEntity<OR<Void>> register(@Valid RegisterRO ro) {
        authService.register(ro);
        return process(this::successResult);
    }

    @ApiOperation(value = "重置密码")
    @PostMapping("/resetPassword")
    public ResponseEntity<OR<Void>> resetPassword(String email, String password, String code) {
        authService.resetPassword(email, password, code);
        return process(this::successResult);
    }

    @ApiOperation(value = "修改用户头像")
    @PostMapping("/updateAvatar")
    public ResponseEntity<OR<Void>> updateAvatar(String email, String avatar) {
        authService.updateAvatar(email, avatar);
        return process(this::successResult);
    }


    @ApiOperation(value = "验证码登录")
    @PostMapping("/emailLogin")
    public ResponseEntity<OR<LoginVO>> emailLogin(String email, String code) {
        LoginVO addUserRO = authService.emailLogin(email, code);
        return processData(() -> addUserRO, this::processException);

    }


    @ApiOperation(value = "收藏视频")
    @PostMapping("/collect")
    public ResponseEntity<OR<Void>> emailLogin(String ffid) {
        UserEntity userEntity = hostHolder.get();
        CollectionEntity entity = new CollectionEntity();
        entity.setUserId(userEntity.getId());
        entity.setVideoId(Long.parseLong(ffid));
        entity.setCreateTime(new Date());
        entity.setUpdateTime(new Date());
        collectionMapper.insert(entity);

        LambdaQueryWrapper<VideosEntity> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(VideosEntity::getId, Long.parseLong(ffid));
        VideosEntity videosEntity = videosEntityMapper.selectOne(wrapper);

        redisTemplate.opsForSet().add(userEntity.getUsername() + "收藏", videosEntity);

        return process(this::successResult);
    }

    @ApiOperation(value = "查询收藏")
    @PostMapping("/selectCollection")
    public ResponseEntity<OR<List<VideosEntityVO>>> emailLogin() {
        UserEntity userEntity = hostHolder.get();

        Set<Object> members = redisTemplate.opsForSet().members(userEntity.getUsername() + "收藏");
        ArrayList<VideosEntity> list = new ArrayList<>();

        for (Object member : members) {
            list.add((VideosEntity) member);
        }

        List<VideosEntityVO> videosEntityVOS = new ArrayList<>();
        list.forEach(videosEntity -> {
            QueryWrapper<CommentEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("video_id", videosEntity.getId());
            List<CommentEntity> commentEntities = commentMapper.selectList(queryWrapper);
            VideosEntityVO videosEntityVO = new VideosEntityVO();
            BeanUtils.copyProperties(videosEntity, videosEntityVO);

            if (commentEntities.size() != 0) {
                List<String> commentInfoList = commentEntities.stream().map(CommentEntity::getCommentInfo).toList();
                videosEntityVO.setCommentList(commentInfoList);
                videosEntityVO.setCommentCount(commentInfoList.size());
            }
            QueryWrapper<CollectionEntity> collectionEntityQueryWrapper = new QueryWrapper<>();
            collectionEntityQueryWrapper.eq("video_id", videosEntity.getId());
            Long collectionCount = collectionMapper.selectCount(collectionEntityQueryWrapper);
            videosEntityVO.setCollectionCount(collectionCount);
            QueryWrapper<VideosInfoEntity> videosInfoEntityQueryWrapper = new QueryWrapper<>();
            videosInfoEntityQueryWrapper.eq("video_id", videosEntity.getId());
            List<VideosInfoEntity> videosInfoEntities = videosInfoEntityMapper.selectList(videosInfoEntityQueryWrapper);
            if (videosInfoEntities.size() != 0) {
                long userId = videosInfoEntities.get(0).getUserId();
                UserEntity user = userEntityMapper.selectById(userId);
                videosEntityVO.setAuthorAvatar(user.getUserAvatar());
                List<Long> tagIdList = videosInfoEntities.stream().map(VideosInfoEntity::getTagId).toList();
                List<String> tagNameList = videosTagMapper.selectBatchIds(tagIdList).stream().map(VideosTagEntity::getTagName).toList();
                videosEntityVO.setTagNameList(tagNameList);
            }
            videosEntityVOS.add(videosEntityVO);
        });


        return processData(() -> videosEntityVOS, "操作成功", this::processException);
    }


    @ApiOperation(value = "查询是否是关注")
    @PostMapping("/selectguanzhu")
    public ResponseEntity<OR<Boolean>> selectguanzhu(Long ffid) {

        LambdaQueryWrapper<VideosInfoEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(VideosInfoEntity::getVideoId, ffid);
        VideosInfoEntity videosInfoEntity = videosInfoEntityMapper.selectOne(wrapper);

        long userId = videosInfoEntity.getUserId();
        LambdaQueryWrapper<UserEntity> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(UserEntity::getId, userId);

        UserEntity userEntity = userEntityMapper.selectOne(wrapper1);

        UserEntity user = hostHolder.get();
        Boolean member = redisTemplate.opsForSet().isMember(userEntity.getUsername() + "粉丝", user.getId());
        return processData(() -> member, "操作成功", this::processException);


    }

}
