package com.yali.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.message.domain.po.NoticeTemplate;
import com.yali.message.domain.po.PublicNotice;

/**
 * <p>
 * 公告消息模板 服务类
 * </p>
 */
public interface IPublicNoticeService extends IService<PublicNotice> {

    void saveNoticeOfTemplate(NoticeTemplate noticeTemplate);
}
