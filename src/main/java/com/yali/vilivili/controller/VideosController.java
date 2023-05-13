package com.yali.vilivili.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.mapper.*;
import com.yali.vilivili.model.entity.*;
import com.yali.vilivili.model.ro.VideosClassifyRO;
import com.yali.vilivili.model.ro.VideosRo;
import com.yali.vilivili.model.vo.ClassifyVideosVO;
import com.yali.vilivili.model.vo.MyVideoVo;
import com.yali.vilivili.model.vo.VideosEntityVO;
import com.yali.vilivili.service.FileUploadService;
import com.yali.vilivili.service.VideosService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * 视频接口
 *
 * @author fuqianlin
 * @date 2023-01-22 15:19
 **/

@RestController
@RequestMapping("/video")
@Api(value = "视频相关", tags = {"视频相关"})
public class VideosController extends BaseController {

    @Resource
    private FileUploadService fileUploadService;

    @Resource
    private VideosService videosService;

    @Resource
    VideosInfoEntityMapper videosInfoEntityMapper;

    @Resource
    VideosEntityMapper videosEntityMapper;

    @Resource
    VideosTagMapper videosTagMapper;

    @Resource
    UserEntityMapper userEntityMapper;

    @Resource
    VideosCategoriesMapper videosCategoriesEntityMapper;

    @Value("${server.port}")
    private String port;

    @Value("${file.upload.context-path}")
    private String contextPath;


    @ApiOperation(value = "视频上传")
    @PostMapping("/addVideo")
    public ResponseEntity<OR<Void>> videoUpload(@RequestParam("videos") MultipartFile videos, VideosRo videosRo) {
        fileUploadService.videoUpload(videos, videosRo);
        return process(this::successResult);
    }

    @ApiOperation(value = "获取视频列表")
    @GetMapping("/getVideosList")
    public ResponseEntity<OR<List<VideosEntity>>> getVideosList() {
        videosService.getVideosList();
        return processData(() -> videosService.getVideosList(), "获取成功", this::processException);
    }

    @ApiOperation(value = "分页获取视频列表")
    @PostMapping("/getVideosListByPage")
    public ResponseEntity<OR<List<VideosEntity>>> getVideosListByPage(Integer page, Integer size, Integer status) {
        videosService.getVideosListByPage(page, size, status);
        return processData(() -> videosService.getVideosListByPage(page, size, status), "获取成功", this::processException);
    }

    @ApiOperation(value = "视频滚动加载接口")
    @PostMapping("/getVideosByCursor")
    public ResponseEntity<OR<List<VideosEntityVO>>> getVideosByCursor(Integer cursor, Integer size, Integer status) {
        videosService.getVideosListByCursor(cursor, size, status);
        return processData(() -> videosService.getVideosListByCursor(cursor, size, status), "获取成功", this::processException);
    }

    @ApiOperation(value = "分类视频")
    @PostMapping("/getVideosByTag")
    public ResponseEntity<OR<List<VideosEntityVO>>> getVideosByTag(VideosClassifyRO ro) {
        String url = "";
        try {
            url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/";
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        QueryWrapper<VideosInfoEntity> videosInfoEntityQueryWrapper = new QueryWrapper<>();
        List<VideosEntityVO> videosEntityVOS = new ArrayList<>();
        if (StringUtils.equals(ro.getCategoriesName(), "全部") || StringUtils.isBlank(ro.getCategoriesName())) {

            Page<VideosEntity> page = new Page<>(ro.getCurrentPage(), ro.getPageSize());
            IPage<VideosEntity> videosEntityIPage = videosEntityMapper.selectPage(page, null);
            List<VideosEntity> videosEntities = videosEntityIPage.getRecords();
            String finalUrl = url;
            videosEntities.forEach(videosEntity -> {
                QueryWrapper<VideosInfoEntity> videosInfoEntityQueryWrapper1 = new QueryWrapper<>();
                videosInfoEntityQueryWrapper1.eq("video_id", videosEntity.getId());
                List<VideosInfoEntity> videosInfoEntities = videosInfoEntityMapper.selectList(videosInfoEntityQueryWrapper1);
                VideosEntityVO videosEntityVO = new VideosEntityVO();
                if (videosInfoEntities.size() != 0) {
                    long userId = videosInfoEntities.get(0).getUserId();
                    UserEntity user = userEntityMapper.selectById(userId);
                    videosEntityVO.setAuthorName(user.getUsername());
                    videosEntityVO.setAuthorAvatar(fileUploadService.getImageUrl(user.getUserAvatar()));
                }
                BeanUtils.copyProperties(videosEntity, videosEntityVO);
                videosEntityVO.setVideosCover(finalUrl + videosEntity.getVideosCover());
                videosEntityVO.setVideosAddress(finalUrl + videosEntity.getVideosAddress());
                videosEntityVOS.add(videosEntityVO);
            });
            return processData(() -> videosEntityVOS, "获取分类视频成功", this::processException);
        }
        QueryWrapper<VideosCategoriesEntity> videosCategoriesEntityQueryWrapper = new QueryWrapper<>();
        videosCategoriesEntityQueryWrapper.eq("categories_name", ro.getCategoriesName());
        VideosCategoriesEntity videosCategoriesEntity = videosCategoriesEntityMapper.selectOne(videosCategoriesEntityQueryWrapper);
        videosInfoEntityQueryWrapper.eq("categories_id", videosCategoriesEntity.getId());

        List<VideosInfoEntity> videosInfoEntities = videosInfoEntityMapper.selectList(videosInfoEntityQueryWrapper);
        List<Long> videoIdList = new ArrayList<>();
        videosInfoEntities.forEach(videosInfoEntity -> {
            videoIdList.add(videosInfoEntity.getVideoId());
        });
        if (videoIdList.size() == 0) {
            return processData(() -> videosEntityVOS, "没有该分类视频", this::processException);
        }
        Page<VideosEntity> page = new Page<>(ro.getCurrentPage(), ro.getPageSize());
        QueryWrapper<VideosEntity> videosEntityQueryWrapper = new QueryWrapper<>();
        videosEntityQueryWrapper.in("id", videoIdList);
        IPage<VideosEntity> videosEntityIPage = videosEntityMapper.selectPage(page, videosEntityQueryWrapper);
        List<VideosEntity> videosEntities = videosEntityIPage.getRecords();
        String finalUrl1 = url;
        videosEntities.forEach(videosEntity -> {
            QueryWrapper<VideosInfoEntity> videosInfoEntityQueryWrapper1 = new QueryWrapper<>();
            videosInfoEntityQueryWrapper1.eq("video_id", videosEntity.getId());
            List<VideosInfoEntity> videosInfoEntities1 = videosInfoEntityMapper.selectList(videosInfoEntityQueryWrapper1);
            long userId = videosInfoEntities1.get(0).getUserId();
            UserEntity user = userEntityMapper.selectById(userId);
            VideosEntityVO videosEntityVO = new VideosEntityVO();
            BeanUtils.copyProperties(videosEntity, videosEntityVO);
            videosEntityVO.setAuthorName(user.getUsername());
            videosEntityVO.setAuthorAvatar(fileUploadService.getImageUrl(user.getUserAvatar()));
            videosEntityVO.setVideosCover(finalUrl1 + videosEntity.getVideosCover());
            videosEntityVO.setVideosAddress(finalUrl1 + videosEntity.getVideosAddress());
            videosEntityVOS.add(videosEntityVO);
        });
        return processData(() -> videosEntityVOS, "获取分类视频成功", this::processException);
    }

    @ApiOperation(value = "热门视频")
    @PostMapping("/hotVideos")
    public ResponseEntity<OR<List<ClassifyVideosVO>>> hotVideos() {
        QueryWrapper<VideosEntity> videosEntityQueryWrapper = new QueryWrapper<>();
        videosEntityQueryWrapper.orderByDesc("play_count");
        videosEntityQueryWrapper.last("limit 0,15");
        List<VideosEntity> videosEntities = videosEntityMapper.selectList(videosEntityQueryWrapper);
        List<ClassifyVideosVO> classifyVideosVOS = new ArrayList<>();
        final int[] rank = {videosEntities.size()};
        videosEntities.forEach(videosEntity -> {
            ClassifyVideosVO classifyVideosVO = new ClassifyVideosVO();
            BeanUtils.copyProperties(videosEntity, classifyVideosVO);
            String url = "";
            try {
                url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/";
            } catch (UnknownHostException e) {
                throw new RuntimeException(e);
            }
            classifyVideosVO.setImg(url + videosEntity.getVideosCover());
            classifyVideosVO.setDescribe(videosEntity.getDescription());
            classifyVideosVO.setPlayNumber(videosEntity.getPlayCount());
            classifyVideosVO.setPlayTime(videosEntity.getVideosTime());
            classifyVideosVO.setRank(rank[0]);
            rank[0] = rank[0] - 1;
            QueryWrapper<VideosInfoEntity> videosInfoEntityQueryWrapper = new QueryWrapper<>();
            List<VideosInfoEntity> videosInfoEntities = videosInfoEntityMapper.selectList(videosInfoEntityQueryWrapper);
            long userId = videosInfoEntities.get(0).getUserId();
            UserEntity user = userEntityMapper.selectById(userId);
            classifyVideosVO.setAuthor(user.getUsername());
            classifyVideosVOS.add(classifyVideosVO);
        });
        return processData(() -> classifyVideosVOS, "获取成功", this::processException);
    }

    @ApiOperation(value = "增加观看次数")
    @PostMapping("/addPlayCount")
    public ResponseEntity<OR<String>> getVideosByCursor(long videoId) {
        VideosEntity videosEntity = videosEntityMapper.selectById(videoId);
        videosEntity.setPlayCount(videosEntity.getPlayCount() + 1);
        videosEntityMapper.updateById(videosEntity);
        return processData(() -> "增加观看次数成功", this::processException);
    }

    @Resource
    CommentMapper commentMapper;

    @Resource
    CollectionMapper collectionMapper;

    @ApiOperation(value = "根据id获取视频信息")
    @PostMapping("/getVideoInfoById")
    public ResponseEntity<OR<VideosEntityVO>> getVideoInfoById(long videoId) {
        VideosEntityVO videosEntityVO = new VideosEntityVO();
        VideosEntity videosEntity = videosEntityMapper.selectById(videoId);
        QueryWrapper<CommentEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("video_id", videoId);
        long commentCount = commentMapper.selectCount(queryWrapper);
        BeanUtils.copyProperties(videosEntity, videosEntityVO);
        videosEntityVO.setCommentCount(commentCount);
        QueryWrapper<CollectionEntity> collectionEntityQueryWrapper = new QueryWrapper<>();
        collectionEntityQueryWrapper.eq("video_id", videosEntity.getId());
        Long collectionCount = collectionMapper.selectCount(collectionEntityQueryWrapper);
        videosEntityVO.setCollectionCount(collectionCount);
        QueryWrapper<VideosInfoEntity> videosInfoEntityQueryWrapper = new QueryWrapper<>();
        videosInfoEntityQueryWrapper.eq("video_id", videoId);
        List<VideosInfoEntity> videosInfoEntities = videosInfoEntityMapper.selectList(videosInfoEntityQueryWrapper);
        String url = "";
        try {
            url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/";
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        if (videosInfoEntities.size() != 0) {
            long userId = videosInfoEntities.get(0).getUserId();
            UserEntity user = userEntityMapper.selectById(userId);
            videosEntityVO.setAuthorAvatar(fileUploadService.getImageUrl(user.getUserAvatar()));
            videosEntityVO.setAuthorName(user.getUsername());
            List<Long> tagIdList = videosInfoEntities.stream().map(VideosInfoEntity::getTagId).toList();
            List<String> tagNameList = videosTagMapper.selectBatchIds(tagIdList).stream().map(VideosTagEntity::getTagName).toList();
            videosEntityVO.setTagNameList(tagNameList);
            videosEntityVO.setVideosAddress(url + videosEntity.getVideosAddress());
            videosEntityVO.setVideosCover(url + videosEntity.getVideosCover());
        }
        return processData(() -> videosEntityVO, this::processException);
    }

    @Resource
    LikeMapper likeMapper;

    @ApiOperation(value = "点赞")
    @PostMapping("/like")
    public ResponseEntity<OR<String>> like(long videoId) {
        LikeEntity like = new LikeEntity();
        like.setLikeTime(new Date());
        like.setVideoId(videoId);
        like.setUserId(this.currentUser());
        likeMapper.insert(like);
        VideosEntity videosEntity = videosEntityMapper.selectById(videoId);
        videosEntity.setLikeCount(videosEntity.getLikeCount() + 1);
        videosEntityMapper.updateById(videosEntity);
        return processData(() -> "点赞成功", this::processException);
    }

    @ApiOperation(value = "我的视频")
    @PostMapping("/myVideos")
    public ResponseEntity<OR<List<MyVideoVo>>> myVideos() {
        QueryWrapper<VideosInfoEntity> videosInfoEntityQueryWrapper = new QueryWrapper<>();
        videosInfoEntityQueryWrapper.eq("user_id", currentUser());
        List<Long> videoIdList = videosInfoEntityMapper.selectList(videosInfoEntityQueryWrapper).stream().map(VideosInfoEntity::getVideoId).distinct().toList();
        List<VideosEntity> videosEntities = videosEntityMapper.selectBatchIds(videoIdList);
        List<MyVideoVo> myVideoVos = new ArrayList<>();
        String url = "";
        try {
            url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/";
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String finalUrl = url;
        videosEntities.forEach(videosEntity -> {
            MyVideoVo myVideoVo = new MyVideoVo();
            myVideoVo.setId(videosEntity.getId());
            myVideoVo.setDescription(videosEntity.getDescription());
            myVideoVo.setLikeCount(videosEntity.getLikeCount());
            myVideoVo.setVideosCover(finalUrl + videosEntity.getVideosCover());
            myVideoVo.setTitle(videosEntity.getTitle());
            myVideoVos.add(myVideoVo);
        });
        return processData(() -> myVideoVos, this::processException);
    }

    @ApiOperation(value = "我喜欢的视频")
    @PostMapping("/myLikeVideos")
    public ResponseEntity<OR<List<MyVideoVo>>> myLikeVideos() {
        QueryWrapper<LikeEntity> likeEntityQueryWrapper = new QueryWrapper<>();
        likeEntityQueryWrapper.eq("user_id", currentUser());
        List<Long> likeVideoIdList = likeMapper.selectList(likeEntityQueryWrapper).stream().map(LikeEntity::getVideoId).toList();
        List<VideosEntity> videosEntities = videosEntityMapper.selectBatchIds(likeVideoIdList);
        List<MyVideoVo> likeVideos = new ArrayList<>();
        String url = "";
        try {
            url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/";
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        String finalUrl = url;
        videosEntities.forEach(videosEntity -> {
            MyVideoVo myVideoVo = new MyVideoVo();
            myVideoVo.setId(videosEntity.getId());
            myVideoVo.setDescription(videosEntity.getDescription());
            myVideoVo.setLikeCount(videosEntity.getLikeCount());
            myVideoVo.setVideosCover(finalUrl + videosEntity.getVideosCover());
            myVideoVo.setTitle(videosEntity.getTitle());
            likeVideos.add(myVideoVo);
        });
        return processData(() -> likeVideos, this::processException);
    }

    @Resource
    AttentionMapper attentionMapper;

    @ApiOperation(value = "我关注的人发布的视频")
    @PostMapping("/myAttentionVideos")
    public ResponseEntity<OR<List<VideosEntityVO>>> myAttentionVideos() {
        QueryWrapper<AttentionEntity> attentionEntityQueryWrapper = new QueryWrapper<>();
        attentionEntityQueryWrapper.eq("user_id", currentUser());
        List<AttentionEntity> attentionEntities = attentionMapper.selectList(attentionEntityQueryWrapper);
        if (attentionEntities.size() == 0) {
            throw new RuntimeException("你还没有关注的人");
        }
        List<Long> attentionIdList = attentionEntities.stream().map(AttentionEntity::getAttentionId).toList();
        QueryWrapper<VideosInfoEntity> videosInfoEntityQueryWrapper = new QueryWrapper<>();
        videosInfoEntityQueryWrapper.in("user_id", attentionIdList);
        List<VideosInfoEntity> videosInfoEntities = videosInfoEntityMapper.selectList(videosInfoEntityQueryWrapper);
        if (videosInfoEntities.size() == 0) {
            throw new RuntimeException("你关注的人都没有过发布视频");
        }
        String url ="";
        try {
             url="http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/";
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }

        List<Long> videoIdList = videosInfoEntities.stream().map(VideosInfoEntity::getVideoId).toList();
        List<VideosEntity> videosList = videosEntityMapper.selectBatchIds(videoIdList);

        List<VideosEntityVO> videosEntityVOS = new ArrayList<>();
        String finalUrl = url;
        videosList.forEach(videosEntity -> {
            QueryWrapper<CommentEntity> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("video_id", videosEntity.getId());
            List<CommentEntity> commentEntities = commentMapper.selectList(queryWrapper);
            VideosEntityVO videosEntityVO = new VideosEntityVO();
            BeanUtils.copyProperties(videosEntity, videosEntityVO);
            videosEntityVO.setVideosAddress(finalUrl +videosEntity.getVideosAddress());
            videosEntityVO.setVideosCover(finalUrl +videosEntity.getVideosCover());
            if (commentEntities.size() != 0) {
                List<String> commentInfoList = commentEntities.stream().map(CommentEntity::getCommentInfo).toList();
                videosEntityVO.setCommentList(commentInfoList);
                videosEntityVO.setCommentCount(commentInfoList.size());
            }
            QueryWrapper<CollectionEntity> collectionEntityQueryWrapper = new QueryWrapper<>();
            collectionEntityQueryWrapper.eq("video_id", videosEntity.getId());
            Long collectionCount = collectionMapper.selectCount(collectionEntityQueryWrapper);
            videosEntityVO.setCollectionCount(collectionCount);
            long userId = videosInfoEntities.get(0).getUserId();
            UserEntity user = userEntityMapper.selectById(userId);
            String avatarUrl = fileUploadService.getImageUrl(user.getUserAvatar());
            videosEntityVO.setAuthorAvatar(avatarUrl);
            videosEntityVO.setAuthorName(user.getUsername());
            List<Long> tagIdList = videosInfoEntities.stream().map(VideosInfoEntity::getTagId).toList();
            List<String> tagNameList = videosTagMapper.selectBatchIds(tagIdList).stream().map(VideosTagEntity::getTagName).toList();
            videosEntityVO.setTagNameList(tagNameList);
            videosEntityVOS.add(videosEntityVO);
        });

        return processData(() -> videosEntityVOS, this::processException);
    }
}
