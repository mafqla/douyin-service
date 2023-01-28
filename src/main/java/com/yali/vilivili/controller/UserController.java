package com.yali.vilivili.controller;

import com.yali.vilivili.annotation.RequireLogin;
import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.ro.UserSelectRO;
import com.yali.vilivili.model.ro.deleteByUserIdRO;
import com.yali.vilivili.model.ro.updateAndSaveUserRO;
import com.yali.vilivili.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;


/**
 * @Description 用户管理
 * @Date 2023.1.4 16:34
 * @Author  fuqianlin
 */
@RestController
@Api(value = "用户管理", tags = {"用户管理"})
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "更新和保存用户")
    @PostMapping("/updateAndSaveUser")
    @RequireLogin
    public ResponseEntity<OR<Void>> updateAndSaveUser(@Valid updateAndSaveUserRO ro) {
        userService.updateAndSaveUser(ro);
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
}
