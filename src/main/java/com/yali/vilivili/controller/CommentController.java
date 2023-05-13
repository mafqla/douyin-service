package com.yali.vilivili.controller;

import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.entity.CommentEntity;
import com.yali.vilivili.model.entity.Reply;
import com.yali.vilivili.repository.CommentRepository;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.repository.VideosRepository;
import com.yali.vilivili.utils.HostHolder;
import com.yali.vilivili.utils.IpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * @author yujie
 * @createTime 2023/5/13 17:10
 * @description
 */
@RestController
@Api(value = "评论接口", tags = {"评论接口"})
@RequestMapping("/comment")
public class CommentController extends BaseController {



    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private VideosRepository videosRepository;

    @Autowired
    private UserRepository userRepository;


    @PostMapping("/add")
    @ApiOperation(value = "增加评论")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "videoId", value = "视频id"),
            @ApiImplicitParam(name = "commentInfo", value = "评论内容"),
    })
    public ResponseEntity<OR<Void>> add(@ApiIgnore CommentEntity commentEntity, HttpServletRequest request) {
        commentEntity.setCommentIp(IpUtils.getIpAddress(request));
        commentEntity.setCommentDislike(0);
        commentEntity.setCommentLike(0);
        commentEntity.setUid(hostHolder.get().getId());
        commentEntity.setCommentTime(new Date());
        commentRepository.save(commentEntity);
        return process(this::successResult);
    }

    @PostMapping("/list")
    @ApiOperation(value = "当前视频的所有评论")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "vid", value = "当前视频id")
    })
    private ResponseEntity<OR<List<CommentEntity>>> list(@ApiIgnore long vid){
        List<CommentEntity> commentEntities = commentRepository.findAllByVideoId(vid);
        commentEntities.forEach(commentEntity -> {
            commentEntity.setVideosEntity(videosRepository.findAllById(commentEntity.getVideoId()));
            commentEntity.setUser(userRepository.findById(commentEntity.getUid()));
        });
        return processData(()-> commentEntities ,"查询成功", this::processException);

    }



}
