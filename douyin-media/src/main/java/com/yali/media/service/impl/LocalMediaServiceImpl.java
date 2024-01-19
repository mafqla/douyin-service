package com.yali.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yali.media.domain.po.LocalMedia;
import com.yali.media.mapper.LocalMediaMapper;
import com.yali.media.service.ILocalMediaService;
import org.springframework.stereotype.Service;

/**
 * @author fuqianlin
 * @date 2024-01-11 11:27
 **/

@Service
public class LocalMediaServiceImpl extends ServiceImpl<LocalMediaMapper, LocalMedia> implements ILocalMediaService {


    @Override
    public void deleteMediaById() {

    }

    @Override
    public void deleteMediaByIds() {

    }

    @Override
    public void updateMediaById() {

    }

    @Override
    public void queryMediaPage() {

    }
}
