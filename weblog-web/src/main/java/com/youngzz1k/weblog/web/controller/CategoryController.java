package com.youngzz1k.weblog.web.controller;

import com.youngzz1k.weblog.common.aspect.ApiOperationLog;
import com.youngzz1k.weblog.common.utils.Response;
import com.youngzz1k.weblog.web.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@Api(tags = "分类")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/list")
    @ApiOperation(value = "前台获取分类列表")
    @ApiOperationLog(description = "前台获取分类列表")
    public Response findCategoryList() {
        return categoryService.findCategoryList();
    }

}