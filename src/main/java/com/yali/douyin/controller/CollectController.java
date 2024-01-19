package com.yali.vilivili.controller;

import com.yali.vilivili.annotation.RequireLogin;
import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.entity.CollectionEntity;
import com.yali.vilivili.service.CollectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 收藏视频接口
 *
 * @author fuqianlin
 * @date 2023-07-26 13:38
 **/

@RestController
@Api(value = "收藏相关", tags = {"收藏视频接口"})
@RequestMapping("/collect")
public class CollectController extends BaseController {

    @Resource
    private CollectService collectService;

    /**
     * 根据用户id分页获取收藏列表
     *
     * @param user_id 用户id
     * @param page    页码
     * @param size    每页数量
     */
    @ApiOperation(value = "根据用户id分页获取收藏列表")
    @GetMapping("/list")
    public ResponseEntity<OR<List<CollectionEntity>>> getCollectList(Integer user_id, Integer page, Integer size) {
        if (user_id == null || user_id == 0) {
            user_id = currentUser();
        }
        int finalUser_id = user_id;
        return this.processData(() -> collectService.getCollectList(finalUser_id, page, size), "获取成功", this::processException);
    }

    /**
     * 根据用户id和视频id添加收藏或取消收藏
     *
     * @param user_id  用户id
     * @param video_id 视频id
     */
    @ApiOperation(value = "添加收藏或取消收藏")
    @PostMapping("/addOrCancel")
    @RequireLogin
    public ResponseEntity<OR<Void>> addOrCancelCollect(Integer user_id, int video_id) {
        if (user_id == null || user_id == 0) {
            user_id = currentUser();
        }
        int finalUser_id = user_id;
        collectService.addOrCancelCollect(finalUser_id, video_id);
        return process(this::successResult);
    }

}
