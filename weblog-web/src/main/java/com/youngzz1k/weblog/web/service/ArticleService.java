package com.youngzz1k.weblog.web.service;

import com.youngzz1k.weblog.common.utils.Response;
import com.youngzz1k.weblog.web.model.VO.article.FindArticleDetailReqVO;
import com.youngzz1k.weblog.web.model.VO.article.FindIndexArticlePageListReqVO;

public interface ArticleService {
    /**
     * 获取首页文章分页数据
     * @param findIndexArticlePageListReqVO
     * @return
     */
    Response findArticlePageList(FindIndexArticlePageListReqVO findIndexArticlePageListReqVO);

    /**
     * 获取文章详情
     * @param findArticleDetailReqVO
     * @return
     */
    Response findArticleDetail(FindArticleDetailReqVO findArticleDetailReqVO);
}