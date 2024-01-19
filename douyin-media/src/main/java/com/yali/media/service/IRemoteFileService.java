package com.yali.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.media.domain.dto.FileDTO;
import com.yali.media.domain.po.RemoteFile;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件表，可以是普通文件、图片等 服务类
 * </p>
 */
public interface IRemoteFileService extends IService<RemoteFile> {

    FileDTO uploadFile(MultipartFile file);

    FileDTO getFileInfo(Long id);
}
