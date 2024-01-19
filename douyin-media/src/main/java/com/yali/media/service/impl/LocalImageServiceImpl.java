package com.yali.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yali.media.domain.po.LocalImage;
import com.yali.media.mapper.LocalImageMapper;
import com.yali.media.service.ILocalImageService;
import org.springframework.stereotype.Service;

/**
 * @author fuqianlin
 * @date 2024-01-11 15:44
 **/

@Service
public class LocalImageServiceImpl extends ServiceImpl<LocalImageMapper, LocalImage> implements ILocalImageService {
}
