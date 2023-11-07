package com.youngzz1k.weblog.web.service;

import com.youngzz1k.weblog.common.utils.Response;
import com.youngzz1k.weblog.web.model.VO.archive.FindArchiveArticlePageListReqVO;

public interface ArchiveService {
    /**
     * 获取文章归档分页数据
     * @param findArchiveArticlePageListReqVO
     * @return
     */
    Response findArchivePageList(FindArchiveArticlePageListReqVO findArchiveArticlePageListReqVO);
}