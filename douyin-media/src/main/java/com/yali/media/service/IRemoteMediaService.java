package com.yali.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.media.domain.dto.MediaDTO;
import com.yali.media.domain.dto.MediaUploadResultDTO;

import com.yali.media.domain.po.RemoteMedia;
import com.yali.media.domain.vo.VideoPlayVO;

/**
 * <p>
 * 媒资表，主要是视频文件 服务类
 * </p>
 */
public interface IRemoteMediaService extends IService<RemoteMedia> {


    MediaDTO save(MediaUploadResultDTO mediaResult);

    void updateMediaProcedureResult(RemoteMedia remoteMedia);

    void deleteMedia(String fileId);

    VideoPlayVO getPlaySignatureByMediaId(Long mediaId);

}
