package com.yali.vilivili.service;

public interface AttentionService {

    /**
     * 查询用户是否关注视频作者
     *
     * @param userId   用户id
     * @param authorId 视频作者id
     */
    Boolean isAttention(int userId, int authorId);
}
