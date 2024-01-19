package com.yali.message.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yali.common.domain.dto.PageDTO;
import com.yali.message.domain.dto.MessageTemplateDTO;
import com.yali.message.domain.dto.MessageTemplateFormDTO;
import com.yali.message.domain.po.MessageTemplate;
import com.yali.message.domain.query.MessageTemplatePageQuery;

import java.util.List;

/**
 * <p>
 * 第三方短信平台签名和模板信息 服务类
 * </p>
 */
public interface IMessageTemplateService extends IService<MessageTemplate> {

    List<MessageTemplate> queryByNoticeTemplateId(Long id);

    Long saveMessageTemplate(MessageTemplateFormDTO messageTemplateDTO);

    void updateMessageTemplate(MessageTemplateFormDTO messageTemplateDTO);

    PageDTO<MessageTemplateDTO> queryMessageTemplates(MessageTemplatePageQuery pageQuery);

    MessageTemplateDTO queryMessageTemplate(Long id);
}
