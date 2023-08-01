package com.yali.vilivili.service.impl;

import com.yali.vilivili.model.entity.VideosTagEntity;
import com.yali.vilivili.repository.TagRepository;
import com.yali.vilivili.service.TagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 标签
 *
 * @author fuqianlin
 * @date 2023-04-17 00:27
 **/


@Service
public class TagServiceImpl implements TagService {

    @Resource
    private TagRepository tagRepository;


    /**
     * 添加标签
     *
     * @param tagName 标签名
     */
    @Override
    public VideosTagEntity addTag(String tagName) {

        String tag = "#" + tagName;
        VideosTagEntity videosTagEntity = new VideosTagEntity();
        videosTagEntity.setTagName(tag);
        videosTagEntity.setCreateTime(new Date());
        videosTagEntity.setUpdateTime(new Date());
        return tagRepository.save(videosTagEntity);
    }
}
