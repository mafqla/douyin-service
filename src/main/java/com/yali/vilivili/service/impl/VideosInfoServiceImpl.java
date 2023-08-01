package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.UserEntity;
import com.yali.vilivili.model.entity.VideosEntity;
import com.yali.vilivili.model.entity.VideosInfoEntity;
import com.yali.vilivili.model.vo.VideosInfoVO;
import com.yali.vilivili.repository.UserRepository;
import com.yali.vilivili.repository.VideosInfoRepository;
import com.yali.vilivili.repository.VideosRepository;
import com.yali.vilivili.service.AttentionService;
import com.yali.vilivili.service.CollectService;
import com.yali.vilivili.service.LikeService;
import com.yali.vilivili.service.VideosInfoService;
import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;


/**
 * 视频信息
 *
 * @author fuqianlin
 * @date 2023-04-17 00:30
 **/

@Service
@Slf4j
public class VideosInfoServiceImpl implements VideosInfoService {
    @Value("${server.port}")
    private String port;

    @Value("${file.upload.context-path}")
    private String contextPath;
    @Resource
    private VideosInfoRepository videosInfoRepository;

    @Resource
    private VideosRepository videosRepository;

    @Resource
    private UserRepository userRepository;

    @Resource
    private FileUploadServiceImpl fileUploadService;
    @Resource
    private LikeService likeService;

    @Resource
    private CollectService collectService;
    @Resource
    private AttentionService attentionService;

    /**
     * 处理视频地址和封面地址
     *
     * @param address
     */
    @Override
    public String handleAddress(String address) {
        try {
            String url = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + contextPath + "/";
            if (!address.contains(url)) {
                address = url + address;
            }
            return address;
        } catch (UnknownHostException e) {
            throw new MyException("获取本机ip失败", String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }

    /**
     * 根据用户id分页查询视频
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<VideosInfoEntity> getVideosInfoListByUserId(Integer userId, Integer page, Integer size) {
        page = (page - 1) * size;
        try {
            List<VideosInfoEntity> videosInfoList = videosInfoRepository.findAllByUserId(userId, page, size);
            if (videosInfoList.isEmpty()) {
                throw new MyException("404", "没有更多数据了");
            }
            return videosInfoList;
        } catch (MyException e) {
            throw new MyException(e.getMessage(), e.getCode());
        } catch (Exception e) {
            log.error("获取作品列表失败", e);
            throw new MyException("500", "获取作品列表失败");
        }
    }

    /**
     * 根据视频id获取视频详情
     *
     * @param id      视频id
     * @param user_id 用户id
     * @return video 视频详情
     */
    @Override
    public VideosInfoVO getVideosInfoById(Long id, Integer user_id) {
        try {
            // 根据视频id获取视频详情
            VideosEntity videosEntity = videosRepository.findAllById(id);
            if (videosEntity == null) {
                throw new MyException("404", "视频不存在");
            }
            //处理视频封面和视频地址
            String videoUrl = handleAddress(videosEntity.getVideosAddress());
            String videoCover = handleAddress(videosEntity.getVideosCover());

            // 根据视频id获取用户id
            Integer userId = videosInfoRepository.findAllByVideoId(id);
            // 根据用户id获取用户信息
            UserEntity userInfo = userRepository.findUserById(userId);
            String avatarUrl = fileUploadService.getImageUrl(userInfo.getUserAvatar());


            VideosInfoVO videosInfoVO = new VideosInfoVO();
            videosInfoVO.setId(videosEntity.getId());
            videosInfoVO.setTitle(videosEntity.getTitle());
            videosInfoVO.setDescription(videosEntity.getDescription());
            videosInfoVO.setVideosAddress(videoUrl);
            videosInfoVO.setVideosCover(videoCover);
            videosInfoVO.setVideosTime(videosEntity.getVideosTime());
            videosInfoVO.setUploadTime(videosEntity.getUploadTime());
            videosInfoVO.setPlayCount(videosEntity.getPlayCount());
            videosInfoVO.setLikeCount(videosEntity.getLikeCount());
            videosInfoVO.setDislikeCount(videosEntity.getDislikeCount());
            videosInfoVO.setCommentCount(videosEntity.getCommentCount());
            videosInfoVO.setStatus(videosEntity.getStatus());
            videosInfoVO.setIsTop(videosEntity.getIsTop());
            videosInfoVO.setUserName(userInfo.getUsername());
            videosInfoVO.setUserAvatar(avatarUrl);

            if (user_id != null) {
                Boolean isLike = likeService.isLike(user_id, id);
                Boolean isCollect = collectService.isCollect(user_id, id);
                Boolean isFollow = attentionService.isAttention(user_id, userId);
                videosInfoVO.setIsLike(isLike);
                videosInfoVO.setIsCollect(isCollect);
                videosInfoVO.setIsAttention(isFollow);

            }
            return videosInfoVO;

        } catch (
                Exception e) {
            log.error("获取视频详情失败", e);
            throw new MyException("500", "获取视频详情失败");
        }


    }
}
