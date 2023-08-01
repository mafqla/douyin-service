package com.yali.vilivili.service;

import com.yali.vilivili.model.entity.LikeEntity;

import java.util.List;

public interface LikeService {

    /**
     * 获取点赞列表降序
     *
     * @param user_id 用户id
     * @param page    页码
     * @param size    每页数量
     * @return 点赞列表
     */

    List<LikeEntity> getLikeList(int user_id, Integer page, Integer size);

    /**
     * 查询该用户是否点赞该视频
     *
     * @param user_id  用户id
     * @param video_id 视频id
     * @return 是否点赞
     */
    Boolean isLike(int user_id, Long video_id);
}
