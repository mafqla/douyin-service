package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.LikeEntity;
import com.yali.vilivili.repository.LikeRepository;
import com.yali.vilivili.service.LikeService;
import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

            List<LikeEntity> likeList = likeRepository.findAllByUserIdOrderByCreateTimeDesc(user_id, page, size);
            if (likeList.size() == 0) {
                throw new MyException("404", "没有更多数据了");
            }
            return likeList;

        } catch (MyException e) {
            throw new MyException(e.getMessage(), e.getCode());
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
}
