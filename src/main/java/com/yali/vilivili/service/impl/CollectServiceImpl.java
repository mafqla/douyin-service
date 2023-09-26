package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.CollectionEntity;
import com.yali.vilivili.model.entity.VideosEntity;
import com.yali.vilivili.repository.CollectRepository;
import com.yali.vilivili.repository.VideosRepository;
import com.yali.vilivili.service.CollectService;
import com.yali.vilivili.utils.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 收藏
 *
 * @author fuqianlin
 * @date 2023-07-26 13:50
 **/

@Service
@Slf4j
public class CollectServiceImpl implements CollectService {
    @Resource
    private CollectRepository collectRepository;

    @Resource
    private VideosRepository videosRepository;

    /**
     * 根据用户id分页获取收藏列表
     *
     * @param user_id 用户id
     * @param page    页码
     * @param size    每页数量
     */
    @Override
    public List<CollectionEntity> getCollectList(int user_id, Integer page, Integer size) {
        page = (page - 1) * size;
        try {
            //先判断是否存在收藏
            long count = collectRepository.countbyUserId(user_id);
            if (count == 0) {
                throw new MyException("404", "暂无内容");
            }

            List<CollectionEntity> collectionList = collectRepository.findByUserId(user_id, page, size);
            if (collectionList.isEmpty()) {
                throw new MyException("204", "没有更多数据了");
            }
            return collectionList;

        } catch (MyException e) {
            throw new MyException(e.getCode(), e.getMessage());
        } catch (Exception e) {
            log.error("获取收藏列表失败", e);
            throw new MyException("500","获取收藏列表失败");
        }
    }

    /**
     * 根据用户id和视频id添加收藏或取消收藏
     *
     * @param user_id  用户id
     * @param video_id 视频id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addOrCancelCollect(int user_id, int video_id) {
        try {
            // 判断用户是否已经收藏了该视频
            List<CollectionEntity> isCollected = collectRepository.findByUserIdAndVideoId(user_id, video_id);

            // 判断视频是否存在
            Optional<VideosEntity> isExist = videosRepository.findById((long) video_id);


            if (!isCollected.isEmpty()) {
                // 如果已经收藏了，则执行取消收藏操作
                collectRepository.deleteById(user_id, video_id);


            } else {
                CollectionEntity collect = new CollectionEntity();
                collect.setCreateTime(new Date());
                collect.setUpdateTime(new Date());
                collect.setUserId(user_id);
                collect.setVideoId(video_id);
                // 如果还未收藏，则执行添加收藏操作
                if (isExist.isEmpty()) {
                    throw new MyException("404", "视频不存在");
                }
                collectRepository.save(collect);
            }

        } catch (MyException e) {
            throw new MyException(e.getMessage(), e.getCode());
        } catch (Exception e) {
            log.error("收藏失败", e);
            throw new MyException("500", "收藏失败");
        }
    }

    /**
     * 查询该用户是否收藏该视频
     *
     * @param user_id  用户id
     * @param video_id 视频id
     */
    @Override
    public Boolean isCollect(int user_id, long video_id) {
        try {
            List<CollectionEntity> isCollected = collectRepository.findByUserIdAndVideoId(user_id, video_id);
            return !isCollected.isEmpty();
        } catch (Exception e) {
            log.error("查询是否收藏失败", e);
            throw new MyException("500", "查询是否收藏失败");
        }
    }
}
