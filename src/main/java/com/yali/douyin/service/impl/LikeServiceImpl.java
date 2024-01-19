package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.LikeEntity;
import com.yali.vilivili.model.entity.VideosEntity;
import com.yali.vilivili.model.entity.VideosInfoEntity;
import com.yali.vilivili.repository.LikeRepository;
import com.yali.vilivili.repository.VideosInfoRepository;
import com.yali.vilivili.repository.VideosRepository;
import com.yali.vilivili.service.LikeService;
import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 点赞管理
 *
 * @author fuqianlin
 * @date 2023-07-25 21:08
 **/
@Service
@Slf4j
public class LikeServiceImpl implements LikeService {

    @Resource
    private LikeRepository likeRepository;
    @Resource
    private VideosRepository videosRepository;

    /**
     * 点赞
     *
     * @param user_id  用户id
     * @param video_id 视频id
     */
    @Override
    public void like(int user_id, Long video_id) {
        try {
            LikeEntity existingLike = likeRepository.findAllByUserIdAndVideoId(user_id, video_id);

            //查询视频是否存在
            VideosEntity videosEntity = videosRepository.findById(video_id).orElse(null);
            if (videosEntity == null) {
                throw new MyException("404", "视频不存在");
            }

            if (existingLike == null) {
                // 用户还没有点赞，执行点赞操作
                LikeEntity likeEntity = new LikeEntity();
                likeEntity.setUserId(user_id);
                likeEntity.setVideoId(video_id);
                likeEntity.setLikeTime(new Date());
                likeRepository.save(likeEntity);

                videosEntity.setLikeCount(videosEntity.getLikeCount() + 1);
                videosRepository.save(videosEntity);
                throw new MyException("200", "点赞成功");
            } else {
                // 用户已经点赞，执行取消点赞操作
                likeRepository.delete(existingLike);

                // 减少视频的点赞数量
//                VideosEntity videosEntity = videosRepository.findById(video_id).orElse(null);
                videosEntity.setLikeCount(videosEntity.getLikeCount() - 1);
                videosRepository.save(videosEntity);

                throw new MyException("200", "取消点赞成功");
            }
        } catch (MyException e) {
            throw new MyException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("点赞失败", e);
            throw new MyException("500", "点赞失败");
        }
    }

    /**
     * 获取点赞列表降序
     *
     * @param user_id 用户id
     * @param page    页码
     * @param size    每页数量
     * @return 点赞列表
     */
    @Override
    public List<LikeEntity> getLikeList(int user_id, Integer page, Integer size) {
        page = (page - 1) * size;
        try {
            //先判断是否存在点赞
            long count = likeRepository.countbyUserId(user_id);
            if (count == 0) {
                throw new MyException("404", "暂无内容");
            }

            List<LikeEntity> likeList = likeRepository.findAllByUserIdOrderByCreateTimeDesc(user_id, page, size);
            if (likeList.isEmpty()) {
                throw new MyException("204", "没有更多数据了");
            }
            return likeList;

        } catch (MyException e) {
            throw new MyException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            throw new MyException("500", "获取点赞列表失败");
        }
    }

    /**
     * 查询该用户是否点赞该视频
     *
     * @param user_id  用户id
     * @param video_id 视频id
     * @return 是否点赞
     */
    @Override
    public Boolean isLike(int user_id, Long video_id) {
        try {

            LikeEntity likeEntity = likeRepository.findAllByUserIdAndVideoId(user_id, video_id);
            if (likeEntity == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            log.error("查询点赞失败");
            throw new MyException("500", "查询点赞失败");
        }
    }

    /**
     * 获取当前用户获得的点赞数
     *
     * @param userId 用户id
     * @return 点赞数量
     */

    @Resource
    private VideosInfoRepository videosInfoRepository;

    @Override
    public Long getUserReceivedLikeCount(Integer userId) {
        try {
            //1.先查询当前用户的视频
            List<VideosInfoEntity> videosInfoList = videosInfoRepository.findAllByUserId(userId);
            //2.遍历视频列表，查询每个视频的点赞数量
            long count = 0;
            for (VideosInfoEntity videosInfoEntity : videosInfoList) {
                Long videoId = videosInfoEntity.getVideoId();
                count += videosRepository.findById(videoId).orElse(null).getLikeCount();
            }
            return  count;


        } catch (Exception e) {
            throw new MyException("500", "获取当前用户获得的点赞数失败");
        }

    }


}
