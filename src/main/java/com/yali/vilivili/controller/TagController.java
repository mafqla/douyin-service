package com.yali.vilivili.controller;

import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.entity.VideosTagEntity;
import com.yali.vilivili.model.ro.updateUserRO;
import com.yali.vilivili.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 标签接口
 *
 * @author fuqianlin
 * @date 2023-04-17 01:16
 **/

@RestController
@Api(value = "标签接口", tags = {"标签接口"})
@RequestMapping(value = "/tag")
public class TagController extends BaseController {

    @Resource
    private TagService tagService;

    /**
     * 添加标签
     *
     * @param tagName 标签名
     */

    @ApiOperation(value = "添加标签")
    @PostMapping("/addTag")
    public ResponseEntity<OR<Void>> addTag(String tagName) {
        tagService.addTag(tagName);
        return process(this::successResult);
    }
}
