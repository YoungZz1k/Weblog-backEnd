package com.youngzz1k.weblog.web.service;

import com.youngzz1k.weblog.common.utils.Response;

public interface TagService {
    /**
     * 获取标签列表
     * @return
     */
    Response findTagList();
}