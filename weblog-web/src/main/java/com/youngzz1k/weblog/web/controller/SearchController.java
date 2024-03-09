package com.youngzz1k.weblog.web.controller;

import com.youngzz1k.weblog.common.aspect.ApiOperationLog;
import com.youngzz1k.weblog.common.utils.Response;
import com.youngzz1k.weblog.web.model.VO.search.SearchArticlePageListReqVO;
import com.youngzz1k.weblog.web.service.SearchService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "搜索")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @PostMapping("/article/search")
    @ApiOperation(value = "文章搜索")
    @ApiOperationLog(description = "文章搜索")
    public Response searchArticlePageList(@RequestBody @Validated SearchArticlePageListReqVO searchArticlePageListReqVO) {
        return searchService.searchArticlePageList(searchArticlePageListReqVO);
    }

}