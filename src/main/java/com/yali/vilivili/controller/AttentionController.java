package com.yali.vilivili.controller;

import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.vo.AttentionVO;
import com.yali.vilivili.service.AttentionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 关注
 *
 * @author fuqianlin
 * @date 2023-09-24 21:52
 **/

@RestController
@Api(value = "关注管理", tags = {"关注"})
@RequestMapping(value = "/attention")
public class AttentionController extends BaseController {

    @Resource
    private AttentionService attentionService;


    @ApiOperation(value = "关注/取消关注")
    @PostMapping("/attention")
    public ResponseEntity<OR<Void>> attention(Integer attentionId) {
        attentionService.addAttention(currentUser(), attentionId);
        return process(this::successResult);
    }

    @ApiOperation(value = "查询关注列表")
    @PostMapping("/attentionList")
    public ResponseEntity<OR<List<AttentionVO>>> attentionList(Integer userId) {
        return processData(() -> attentionService.findAttentionList(userId), "查询成功", this::processException);
    }

    @ApiOperation(value = "查询粉丝列表")
    @PostMapping("/fansList")
    public ResponseEntity<OR<List<AttentionVO>>> fansList(Integer userId) {
        return processData(() -> attentionService.findFansList(userId), "查询成功", this::processException);
    }
}
