package com.youngzz1k.weblog.admin.service;

import com.youngzz1k.weblog.admin.model.vo.article.DeleteArticleReqVO;
import com.youngzz1k.weblog.admin.model.vo.article.PublishArticleReqVO;
import com.youngzz1k.weblog.common.utils.Response;

public interface AdminArticleService {
    /**
     * 发布文章
     * @param publishArticleReqVO 发布文章入参
     * @return
     */
    Response publishArticle(PublishArticleReqVO publishArticleReqVO);

    /**
     * 删除文章
     * @param deleteArticleReqVO
     * @return
     */
    Response deleteArticle(DeleteArticleReqVO deleteArticleReqVO);
}
