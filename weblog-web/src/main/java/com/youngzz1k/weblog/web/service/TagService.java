package com.youngzz1k.weblog.web.service;

import com.youngzz1k.weblog.common.utils.Response;
import com.youngzz1k.weblog.web.model.VO.tag.FindTagArticlePageListReqVO;

public interface TagService {
    /**
     * 获取标签列表
     * @return
     */
    Response findTagList();

    /**
     * 获取标签下文章分页列表
     * @param findTagArticlePageListReqVO
     * @return
     */
    Response findTagPageList(FindTagArticlePageListReqVO findTagArticlePageListReqVO);
}