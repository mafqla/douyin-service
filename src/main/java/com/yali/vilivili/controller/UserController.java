package com.yali.vilivili.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yali.vilivili.annotation.RequireLogin;
import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.mapper.AttentionMapper;
import com.yali.vilivili.model.entity.AttentionEntity;
import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.ro.AddUserRO;
import com.yali.vilivili.model.ro.UserSelectRO;
import com.yali.vilivili.model.ro.deleteByUserIdRO;
import com.yali.vilivili.model.ro.updateUserRO;
import com.yali.vilivili.service.UserService;
import com.yali.vilivili.utils.HostHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;


/**
 * @Description 用户管理
 * @Date 2023.1.4 16:34
 * @Author fuqianlin
 */
@RestController
@Api(value = "用户管理", tags = {"用户管理"})
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @Resource
    private HostHolder hostHolder;

    @ApiOperation(value = "更新用户")
    @PostMapping("/updateUser")
    @RequireLogin
    public ResponseEntity<OR<Void>> updateUser(@Valid updateUserRO ro) {
        userService.updateUser(ro);
        return process(this::successResult);
    }

    @ApiOperation(value = "添加用户")
    @PostMapping("/addUser")
    @RequireLogin
    public ResponseEntity<OR<Void>> addUser(@Valid AddUserRO ro) {
        userService.addUser(ro);
        return process(this::successResult);
    }

    @ApiOperation(value = "删除用户")
    @PostMapping("/deleteById")
    @RequireLogin
    public ResponseEntity<OR<Void>> deleteById(@Valid deleteByUserIdRO ro) {
        userService.deleteById(ro);
        return process(this::successResult);
    }

    @ApiOperation(value = "查询用户")
    @PostMapping("/find")
    @RequireLogin
    public ResponseEntity<OR<List<UserEntity>>> findAllUser(@Valid UserSelectRO ro) {
        userService.findUser(ro);
        return processData(() -> userService.findUser(ro),"查询成功", this::processException);
    }

    @ApiOperation(value = "分页查询用户")
    @PostMapping("/findUserByPage")
    public ResponseEntity<OR<List<UserEntity>>> findUserByPage(Integer page, Integer size) {
        userService.findAllUserByPage(page, size);
        return processData(() -> userService.findAllUserByPage(page, size),"查询成功", this::processException);
    }

    @ApiOperation(value = "关注 保存到redis")
    @PostMapping("/attention")
    public ResponseEntity<OR<Void>> attention(String username,String fansname) {
        userService.attention(username,fansname);
        return process(this::successResult);
    }


    @ApiOperation(value = "取关 保存到redis")
    @PostMapping("/cancel")
    public ResponseEntity<OR<Void>> cancel(String username,String fansnams) {
        userService.cancel(username, fansnams);
        return process(this::successResult);
    }

    @ApiOperation(value = "查询粉丝")
    @PostMapping("/selectAttention")
    public ResponseEntity<OR<List<UserEntity>>> cancel() {
        UserEntity userEntity = hostHolder.get();
        List<UserEntity> userEntities = userService.selectAttention(userEntity.getUsername());
        return processData(()->userEntities,this::processException);
    }

    @Resource
    AttentionMapper attentionMapper;

    @ApiOperation(value = "关注/取关 保存到mysql")
    @PostMapping("/attentionNew")
    public ResponseEntity<OR<String>> attentionNew(long attentionId) {
        if(attentionId==currentUser()){
            throw new RuntimeException("自己不能关注自己");
        }
        QueryWrapper<AttentionEntity> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("attention_id", attentionId);
        AttentionEntity existAttention = attentionMapper.selectOne(queryWrapper);
        if(null==existAttention){
            AttentionEntity attention=new AttentionEntity();
            attention.setAttentionId(attentionId);
            attention.setUserId(currentUser());
            attention.setAttentionTime(new Date());
            attentionMapper.insert(attention);
            return processData(()->"关注成功",this::processException);
        }else{
            attentionMapper.deleteById(existAttention.getId());
            return processData(()->"取关成功",this::processException);
        }
    }
}
