package com.yali.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yali.api.client.user.UserClient;
import com.yali.common.utils.*;
import com.yali.media.domain.dto.MediaDTO;
import com.yali.media.domain.dto.MediaUploadResultDTO;
import com.yali.media.domain.po.RemoteMedia;
import com.yali.media.domain.vo.VideoPlayVO;
import com.yali.media.enums.FileStatus;
import com.yali.media.mapper.RemoteMediaMapper;
import com.yali.media.service.IRemoteMediaService;
import com.yali.media.storage.IMediaStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.Media;
import java.util.*;

import static com.yali.media.constants.FileErrorInfo.MEDIA_NOT_EXISTS;

/**
 * <p>
 * 媒资表，主要是视频文件 服务实现类
 * </p>
 */
@Service
@RequiredArgsConstructor(onConstructor_ = {@Lazy})
public class RemoteMediaServiceImpl extends ServiceImpl<RemoteMediaMapper, RemoteMedia> implements IRemoteMediaService {

    private final IMediaStorage mediaStorage;

    private final UserClient userClient;


    @Override
    public VideoPlayVO getPlaySignatureByMediaId(Long mediaId) {
        // 1.根据id查询媒资信息
        RemoteMedia remoteMedia = getById(mediaId);
        // 2.获取签名
        String signature = mediaStorage.getPlaySignature(remoteMedia.getFileId(), UserContext.getUser(), null);
        // 3.返回
        VideoPlayVO vo = new VideoPlayVO();
        vo.setSignature(signature);
        vo.setFileId(remoteMedia.getFileId());
        return vo;
    }


    @Override
    public MediaDTO save(MediaUploadResultDTO result) {
        // 1.查询视频信息
        List<RemoteMedia> list = mediaStorage.queryMediaInfos(result.getFileId());
        AssertUtils.isNotEmpty(list, MEDIA_NOT_EXISTS);
        // 2.判断是否存在，幂等处理
        RemoteMedia remoteMedia = lambdaQuery().eq(RemoteMedia::getFileId, result.getFileId()).one();
        if (remoteMedia != null) {
            // 已经存在并且处理过
            return BeanUtils.toBean(remoteMedia, MediaDTO.class);
        }
        // 3.查询视频信息
        remoteMedia = list.get(0);
        // 4.直接保存数据库
        save(list.get(0));
        return BeanUtils.toBean(remoteMedia, MediaDTO.class);
    }

    @Override
    public void updateMediaProcedureResult(RemoteMedia remoteMedia) {
        // 1.查询fileId是否已经存在
        RemoteMedia old = lambdaQuery().eq(RemoteMedia::getFileId, remoteMedia.getFileId()).one();
        if (old == null) {
            // 2.如果不存在，新增
            save(remoteMedia);
        } else {
            // 3.存在，则更新
            lambdaUpdate()
                    .set(RemoteMedia::getStatus, FileStatus.PROCESSED.getValue())
                    .set(RemoteMedia::getCoverUrl, remoteMedia.getCoverUrl())
                    .eq(RemoteMedia::getId, old.getId())
                    .update();
        }
    }

    @Override
    @Transactional
    public void deleteMedia(String fileId) {
        // 1.删除云端文件
        mediaStorage.deleteFile(fileId);
        // 2.删除本地信息
        remove(new LambdaQueryWrapper<RemoteMedia>().eq(RemoteMedia::getFileId, fileId));
    }
}
