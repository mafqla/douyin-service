package com.yali.vilivili.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yali.vilivili.mapper.VideosInfoEntityMapper;
import com.yali.vilivili.model.entity.VideosInfoEntity;
import com.yali.vilivili.service.VideosInfoService;
import org.springframework.stereotype.Service;

/**
 * 视频信息
 *
 * @author fuqianlin
 * @date 2023-04-17 00:30
 **/

@Service
public class VideosInfoServiceImpl extends ServiceImpl<VideosInfoEntityMapper, VideosInfoEntity> implements VideosInfoService {
}
