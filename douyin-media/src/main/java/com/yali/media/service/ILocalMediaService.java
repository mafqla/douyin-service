package com.yali.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.media.domain.po.LocalMedia;

public interface ILocalMediaService extends IService<LocalMedia> {


    /**
     * 删除视频信息
     */
    void deleteMediaById();

    /**
     * 以数组形式删除多个视频信息
     */
    void deleteMediaByIds();

    /**
     * 更新视频信息
     */
    void updateMediaById();

    /**
     * 分页查询视频
     */
    void queryMediaPage();


}
