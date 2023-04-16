package com.yali.vilivili.controller;

import com.yali.vilivili.controller.base.BaseController;
import com.yali.vilivili.controller.base.OR;
import com.yali.vilivili.model.ro.AddCategoriesRO;
import com.yali.vilivili.model.vo.CategoriesVO;
import com.yali.vilivili.service.CategoriesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * 分类接口
 *
 * @author fuqianlin
 * @date 2023-04-17 01:32
 **/

@RestController
@Api(value = "分类接口", tags = {"分类接口"})
@RequestMapping(value = "/categories")
public class CategoriesController extends BaseController {

    @Resource
    private CategoriesService categoriesService;

    @ApiOperation(value = "添加分类")
    @PostMapping("/addCategories")
    public ResponseEntity<OR<Void>> addCategories(@RequestParam(value = "img", required = false)
                                                  MultipartFile img, @Valid AddCategoriesRO ro) throws IOException {
        categoriesService.addCategories(img, ro);
        return process(this::successResult);
    }

    @ApiOperation(value = "获取分类列表")
    @GetMapping("/getCategoriesList")
    public ResponseEntity<OR<List<CategoriesVO>>> getCategoriesList() {
        return this.processData(() -> categoriesService.getCategoriesList(), "获取成功", this::processException);
    }

}
