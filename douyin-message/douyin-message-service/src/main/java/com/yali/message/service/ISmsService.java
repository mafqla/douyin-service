package com.yali.message.service;

import com.yali.api.dto.sms.SmsInfoDTO;
import com.yali.api.dto.user.UserDTO;
import com.yali.message.domain.po.NoticeTemplate;

import java.util.List;

public interface ISmsService {
    void sendMessageByTemplate(NoticeTemplate noticeTemplate, List<UserDTO> users);

    void sendMessage(SmsInfoDTO smsInfoDTO);


    void sendMessageAsync(SmsInfoDTO smsInfoDTO);
}
