package com.youngzz1k.weblog.web.service;

import com.youngzz1k.weblog.common.utils.Response;
import com.youngzz1k.weblog.web.model.VO.search.SearchArticlePageListReqVO;

public interface SearchService {

    /**
     * 关键词分页搜索
     * @param searchArticlePageListReqVO
     * @return
     */
    Response searchArticlePageList(SearchArticlePageListReqVO searchArticlePageListReqVO);
}