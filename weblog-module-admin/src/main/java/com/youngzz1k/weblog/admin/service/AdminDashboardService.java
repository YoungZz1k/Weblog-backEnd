package com.youngzz1k.weblog.admin.service;

import com.youngzz1k.weblog.common.utils.Response;

public interface AdminDashboardService {

    /**
     * 获取仪表盘基础统计信息
     * @return
     */
    Response findDashboardStatistics();
}