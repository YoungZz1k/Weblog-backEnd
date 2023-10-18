package com.youngzz1k.weblog.admin.service;

import com.youngzz1k.weblog.admin.model.vo.category.AddCategoryReqVO;
import com.youngzz1k.weblog.common.utils.Response;

public interface AdminCategoryService {

    /**
     * 添加分类
     * @param addCategoryReqVO
     * @return
     */
    Response addCategory(AddCategoryReqVO addCategoryReqVO);

}
