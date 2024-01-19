package com.yali.message.controller;


import com.yali.common.domain.dto.PageDTO;
import com.yali.message.domain.dto.MessageTemplateDTO;
import com.yali.message.domain.dto.MessageTemplateFormDTO;
import com.yali.message.domain.query.MessageTemplatePageQuery;
import com.yali.message.service.IMessageTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 第三方短信平台模板信息管理 前端控制器
 * </p>
 */
@Api(tags = "短信模板管理接口")
@RestController
@RequestMapping("/message-templates")
@RequiredArgsConstructor
public class MessageTemplateController {

    private final IMessageTemplateService messageTemplateService;

    @PostMapping
    @ApiOperation("新增短信模板")
    public Long saveMessageTemplate(@RequestBody MessageTemplateFormDTO messageTemplateDTO) {
        return messageTemplateService.saveMessageTemplate(messageTemplateDTO);
    }

    @PutMapping("/{id}")
    @ApiOperation("更新短信模板")
    public void updateMessageTemplate(
            @RequestBody MessageTemplateFormDTO messageTemplateDTO,
            @ApiParam(value = "短信模板id", example = "1") @PathVariable("id") Long id) {
        messageTemplateDTO.setId(id);
        messageTemplateService.updateMessageTemplate(messageTemplateDTO);
    }

    @GetMapping
    @ApiOperation("分页查询短信模板")
    public PageDTO<MessageTemplateDTO> queryMessageTemplates(MessageTemplatePageQuery pageQuery) {
        return messageTemplateService.queryMessageTemplates(pageQuery);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询短信模板")
    public MessageTemplateDTO queryMessageTemplate(@ApiParam(value = "模板id", example = "1") @PathVariable("id") Long id) {
        return messageTemplateService.queryMessageTemplate(id);
    }
}
