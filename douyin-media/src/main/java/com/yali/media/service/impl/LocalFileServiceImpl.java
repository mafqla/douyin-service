package com.yali.media.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yali.media.domain.po.LocalFile;
import com.yali.media.mapper.LocalFileMapper;
import com.yali.media.service.ILocalFileService;
import org.springframework.stereotype.Service;

/**
 * @author fuqianlin
 * @date 2024-01-11 11:28
 **/

@Service
public class LocalFileServiceImpl extends ServiceImpl<LocalFileMapper, LocalFile> implements ILocalFileService {
}
