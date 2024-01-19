package com.yali.vilivili.service;

import com.yali.vilivili.model.vo.AttentionVO;

import java.util.List;

public interface AttentionService {

    /**
     * 查询用户是否关注视频作者
     *
     * @param userId   用户id
     * @param authorId 视频作者id
     */
    Integer isAttention(int userId, int authorId);

    /**
     * 查询当前用户关注列表
     * @param userId 用户id
     */
    List<AttentionVO> findAttentionList(Integer userId);

    /**
     * 查询当前用户粉丝列表
     * @param userId 用户id
     */
    List<AttentionVO> findFansList(Integer userId);

    /**
     * 查询当前用户关注数量
     * @param userId 用户id
     */
    int findAttentionCount(Integer userId);

    /**
     * 查询当前用户粉丝数量
     * @param userId 用户id
     */
    int findFansCount(Integer userId);

    /**
     * 添加关注 / 取消关注
     * @param userId 用户id
     * @param attentionId 关注的用户id
     */
    void addAttention(int userId, int attentionId);
}
