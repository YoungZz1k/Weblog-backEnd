package com.youngzz1k.weblog.web.service;

import com.youngzz1k.weblog.common.utils.Response;

public interface CategoryService {
    /**
     * 获取分类列表
     * @return
     */
    Response findCategoryList();
}