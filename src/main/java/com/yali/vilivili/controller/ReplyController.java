package com.yali.vilivili.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.dao.ReplyDao;
import com.yali.vilivili.model.entity.Reply;
import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.utils.HostHolder;
import com.yali.vilivili.utils.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author yujie
 * @createTime 2023/5/13 16:02
 * @description
 */
@RestController
@RequestMapping("/reply")
@Api(value = "回复接口", tags = {"回复接口"})
public class ReplyController extends BaseController {

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private UserRepository userRepository;

    /**
     * 增加评论
     * @param reply
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "增加回复")
    private ResponseEntity<OR<Void>> add(@RequestBody Reply reply, HttpServletRequest request){
        UserEntity user = hostHolder.get();
        reply.setFromUid(user.getId());
        reply.setDate(new Date());
        reply.setIp(IpUtils.getIpAddress(request));
        replyDao.insert(reply);
        return process(this::successResult);
    }

    /**
     * 获取当前评论下的所有回复
     * @param cid
     * @return
     */
    @GetMapping("/list")
    @ApiOperation(value = "当前评论下的所有回复")
    private ResponseEntity<OR<List<Reply>>> list(Integer cid){
        LambdaQueryWrapper<Reply> replyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        replyLambdaQueryWrapper.eq(Reply::getCid, cid);
        List<Reply> replies = replyDao.selectList(replyLambdaQueryWrapper);
        replies.forEach(reply -> {
            reply.setFromUser(userRepository.findById(reply.getFromUid()));
            reply.setToUser(userRepository.findById(reply.getToUid()));
        });
        return processData(()-> replies ,"查询成功", this::processException);
    }

}
