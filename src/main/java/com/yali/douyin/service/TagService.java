package com.yali.vilivili.service;


import com.yali.vilivili.model.entity.VideosTagEntity;

public interface TagService {

    /**
     * 添加标签
     *
     * @param tagName 标签名
     */
    VideosTagEntity addTag(String tagName);
}
