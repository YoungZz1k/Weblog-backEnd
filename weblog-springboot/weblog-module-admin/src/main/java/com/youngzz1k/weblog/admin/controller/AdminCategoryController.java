package com.youngzz1k.weblog.admin.controller;


import com.youngzz1k.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.youngzz1k.weblog.admin.service.AdminCategoryService;
import com.youngzz1k.weblog.common.aspect.ApiOperationLog;
import com.youngzz1k.weblog.common.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Api(tags = "Admin 分类模块")
public class AdminCategoryController {

    @Autowired
    private AdminCategoryService categoryService;

    @PostMapping("/category/add")
    @ApiOperation(value = "添加分类")
    @ApiOperationLog(description = "添加分类")
    public Response addCategory(@RequestBody @Validated AddCategoryReqVO addCategoryReqVO) {
        return categoryService.addCategory(addCategoryReqVO);
    }


}