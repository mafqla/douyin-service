package com.yali.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.common.domain.dto.PageDTO;
import com.yali.message.domain.dto.SmsThirdPlatformDTO;
import com.yali.message.domain.dto.SmsThirdPlatformFormDTO;
import com.yali.message.domain.po.SmsThirdPlatform;
import com.yali.message.domain.query.SmsThirdPlatformPageQuery;

import java.util.List;

/**
 * <p>
 * 第三方云通讯平台 服务类
 * </p>
 */
public interface ISmsThirdPlatformService extends IService<SmsThirdPlatform> {

    List<SmsThirdPlatform> queryAllPlatform();

    Long saveSmsThirdPlatform(SmsThirdPlatformFormDTO thirdPlatformDTO);

    void updateSmsThirdPlatform(SmsThirdPlatformFormDTO thirdPlatformDTO);

    PageDTO<SmsThirdPlatformDTO> querySmsThirdPlatforms(SmsThirdPlatformPageQuery query);

    SmsThirdPlatformDTO querySmsThirdPlatform(Long id);
}
