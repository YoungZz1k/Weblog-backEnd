package com.youngzz1k.weblog.admin.service;

import com.youngzz1k.weblog.admin.model.vo.blogsettings.UpdateBlogSettingsReqVO;
import com.youngzz1k.weblog.common.utils.Response;

public interface AdminBlogSettingsService {
    /**
     * 更新博客设置信息
     * @param updateBlogSettingsReqVO
     * @return
     */
    Response updateBlogSettings(UpdateBlogSettingsReqVO updateBlogSettingsReqVO);
}
