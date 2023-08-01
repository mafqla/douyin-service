package com.yali.vilivili.service;

import com.yali.vilivili.model.entity.CollectionEntity;

import java.util.List;

/**
 * 收藏
 *
 * @author fuqianlin
 * @date 2023-07-26 13:50
 **/
public interface CollectService {

    /**
     * 根据用户id分页获取收藏列表
     *
     * @param user_id 用户id
     * @param page    页码
     * @param size    每页数量
     */
    List<CollectionEntity> getCollectList(int user_id, Integer page, Integer size);

    /**
     * 根据用户id和视频id添加收藏或取消收藏
     *
     * @param user_id  用户id
     * @param video_id 视频id
     */
    void addOrCancelCollect(int user_id, int video_id);

    /**
     * 查询该用户是否收藏该视频
     *
     * @param user_id  用户id
     * @param video_id 视频id
     */
    Boolean isCollect(int user_id, long video_id);
}
