package com.yali.vilivili.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yali.vilivili.annotation.RequireLogin;
import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.dao.ReplyDao;
import com.yali.vilivili.model.entity.CommentEntity;
import com.yali.vilivili.model.entity.Reply;
import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.repository.CommentRepository;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.utils.HostHolder;
import com.yali.vilivili.utils.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "vid", value = "视频id"),
            @ApiImplicitParam(name = "cid", value = "评论id"),
            @ApiImplicitParam(name = "toUid", value = "被回复人的id"),
            @ApiImplicitParam(name = "msg", value = "回复内容"),
    })
    private ResponseEntity<OR<Void>> add(@ApiIgnore Reply reply, HttpServletRequest request){
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
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "cid", value = "当前评论id")
    })
    private ResponseEntity<OR<List<Reply>>> list( @ApiIgnore Integer cid){
        LambdaQueryWrapper<Reply> replyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        replyLambdaQueryWrapper.eq(Reply::getCid, cid);
        List<Reply> replies = replyDao.selectList(replyLambdaQueryWrapper);
        replies.forEach(reply -> {
            reply.setFromUser(userRepository.findById(reply.getFromUid()));
            reply.setToUser(userRepository.findById(reply.getToUid()));
        });
        return processData(()-> replies ,"查询成功", this::processException);
    }

    @Autowired
    private CommentRepository commentRepository;


    @GetMapping("/action")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "type", value = "操作类型(reply/comment)"),
            @ApiImplicitParam(name = "action", value = "操作类型(like/dislike/cancelLike/cancelDisLike)"),
            @ApiImplicitParam(name = "id", value = "主键id"),
    })
    private ResponseEntity<OR<Void>> action( @ApiIgnore String type, @ApiIgnore String action, @ApiIgnore Object id){
        if(StringUtils.equals(type, "reply")){
            Long replyId = (Long) id;
            if(StringUtils.equals(action, "like")){
                Reply reply = replyDao.selectById(replyId);
                reply.setLike(reply.getLike()+1);
                replyDao.updateById(reply);
            }
            if(StringUtils.equals(action, "dislike")){
                Reply reply = replyDao.selectById(replyId);
                reply.setDislike(reply.getDislike()+1);
                replyDao.updateById(reply);
            }
            if(StringUtils.equals(action, "cancelLike")){
                Reply reply = replyDao.selectById(replyId);
                reply.setLike(reply.getLike()-1);
                replyDao.updateById(reply);
            }
            if(StringUtils.equals(action, "cancelDisLike")){
                Reply reply = replyDao.selectById(replyId);
                reply.setDislike(reply.getDislike()-1);
                replyDao.updateById(reply);
            }
        }
        if(StringUtils.equals(type, "comment")){
            Long commentId = (Long) id;
            if(StringUtils.equals(action, "like")){
                CommentEntity commentEntity = commentRepository.findById(commentId).get();
                commentEntity.setCommentLike(commentEntity.getCommentLike()+1);
                commentRepository.save(commentEntity);
            }
            if(StringUtils.equals(action, "dislike")){
                CommentEntity commentEntity = commentRepository.findById(commentId).get();
                commentEntity.setCommentDislike(commentEntity.getCommentDislike()+1);
                commentRepository.save(commentEntity);
            }
            if(StringUtils.equals(action, "cancelLike")){
                CommentEntity commentEntity = commentRepository.findById(commentId).get();
                commentEntity.setCommentLike(commentEntity.getCommentLike()-1);
                commentRepository.save(commentEntity);
            }
            if(StringUtils.equals(action, "cancelDisLike")){
                CommentEntity commentEntity = commentRepository.findById(commentId).get();
                commentEntity.setCommentDislike(commentEntity.getCommentDislike()-1);
                commentRepository.save(commentEntity);
            }
        }
        return process(this::successResult);
    }

}
