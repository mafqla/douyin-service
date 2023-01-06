package com.yali.vilivili.controller;

import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.User;
import com.yali.vilivili.model.ro.updateAndSaveUserRO;
import com.yali.vilivili.model.ro.UserSelectRO;
import com.yali.vilivili.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<OR<Void>> updateAndSaveUser(@Valid updateAndSaveUserRO ro) {
        userService.updateAndSaveUser(ro);
        return process(this::successResult);
    }

    @ApiOperation(value = "查询用户")
    @PostMapping("/find")
    public ResponseEntity<OR<List<User>>> findAllUser(UserSelectRO ro) {
        userService.findAllUser(ro);
        return processData(()->userService.findAllUser(ro),this::processException);
    }
}
