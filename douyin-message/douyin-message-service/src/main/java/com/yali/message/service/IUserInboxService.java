package com.yali.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.api.dto.user.UserDTO;
import com.yali.common.domain.dto.PageDTO;
import com.yali.message.domain.dto.UserInboxDTO;
import com.yali.message.domain.dto.UserInboxFormDTO;
import com.yali.message.domain.po.NoticeTemplate;
import com.yali.message.domain.po.UserInbox;
import com.yali.message.domain.query.UserInboxQuery;

import java.util.List;

/**
 * <p>
 * 用户通知记录 服务类
 * </p>
 */
public interface IUserInboxService extends IService<UserInbox> {

    void saveNoticeToInbox(NoticeTemplate noticeTemplate, List<UserDTO> users);

    PageDTO<UserInboxDTO> queryUserInBoxesPage(UserInboxQuery query);

    Long sentMessageToUser(UserInboxFormDTO userInboxFormDTO);
}
