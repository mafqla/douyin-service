package com.yali.vilivili.service;

import com.yali.vilivili.model.entity.VideosEntity;

import java.util.List;

public interface VideosService {

    /**
     * 获取视频列表
     *
     * @return 视频列表
     */

    List<VideosEntity> getVideosList();

    /**
     * 分页查询视频列表
     *
     * @param page   页码
     * @param size   每页数量
     * @param status 状态
     */
    List<VideosEntity> getVideosListByPage(Integer page, Integer size, Integer status);

    /**
     * 视频滚动加载接口
     *
     * @param cursor 当前页码
     * @param size   每页数量
     * @param status 状态
     */
    List<VideosEntity> getVideosListByCursor(Integer cursor, Integer size, Integer status);

}
