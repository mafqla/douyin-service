package com.yali.vilivili.service;

import com.yali.vilivili.model.entity.VideosEntity;
import com.yali.vilivili.model.vo.VideosEntityVO;
import com.yali.vilivili.model.vo.VideosParamsVO;

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
    List<VideosEntityVO> getVideosListByCursor(Integer cursor, Integer size, Integer status);

    String handleAddress(String address);

    /**
     * 根据参数获取视频列表
     *
     * @param showTab
     * @param user_id
     * @param page
     * @param size
     * @return
     */
    VideosParamsVO getVideosListByParam(String showTab, int user_id, Integer page, Integer size);
}
