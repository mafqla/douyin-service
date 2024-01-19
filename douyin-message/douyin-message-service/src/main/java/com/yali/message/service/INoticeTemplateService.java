package com.yali.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.common.domain.dto.PageDTO;
import com.yali.message.domain.dto.NoticeTemplateDTO;
import com.yali.message.domain.dto.NoticeTemplateFormDTO;
import com.yali.message.domain.po.NoticeTemplate;
import com.yali.message.domain.query.NoticeTemplatePageQuery;

/**
 * <p>
 * 通知模板 服务类
 * </p>
 */
public interface INoticeTemplateService extends IService<NoticeTemplate> {

    Long saveNoticeTemplate(NoticeTemplateFormDTO noticeTemplateFormDTO);

    void updateNoticeTemplate(NoticeTemplateFormDTO noticeTemplateFormDTO);

    PageDTO<NoticeTemplateDTO> queryNoticeTemplates(NoticeTemplatePageQuery pageQuery);

    NoticeTemplateDTO queryNoticeTemplate(Long id);

    NoticeTemplate queryByCode(String code);
}
