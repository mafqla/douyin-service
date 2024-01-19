package com.yali.vilivili.service;


import com.yali.vilivili.model.entity.VideosInfoEntity;
import com.yali.vilivili.model.vo.VideosInfoVO;

import java.util.List;

public interface VideosInfoService {

    String handleAddress(String address);

    /**
     * 根据用户id分页查询视频
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    List<VideosInfoEntity> getVideosInfoListByUserId(Integer userId, Integer page, Integer size);

    /**
     * 根据视频id获取视频详情
     *
     * @param id      视频id
     * @param user_id 用户id
     * @return video 视频详情
     */
    VideosInfoVO getVideosInfoById(Long id, Integer user_id);
}
