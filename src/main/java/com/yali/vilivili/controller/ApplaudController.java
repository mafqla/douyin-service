package com.yali.vilivili.controller;

import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.service.LikeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 点赞
 *
 * @author fuqianlin
 * @date 2023-09-24 23:14
 **/

@RestController
@Api(value = "点赞管理", tags = {"点赞"})
@RequestMapping(value = "/applaud")
public class ApplaudController extends BaseController {

    @Resource
    private LikeService applaudService;
    @ApiOperation(value = "点赞/取消点赞")
    @PostMapping("/toggleApplaud")
    public ResponseEntity<OR<Void>> applaud(Integer applaudId) {
        applaudService.like(currentUser(), Long.valueOf(applaudId));
        return process(this::successResult);
    }
}
