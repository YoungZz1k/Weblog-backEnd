package com.youngzz1k.weblog.admin.service;

import com.youngzz1k.weblog.common.utils.Response;
import com.youngzz1k.weblog.admin.model.vo.user.UpdateAdminUserPasswordReqVO;

public interface AdminUserService {

    /**
     * 修改密码
     * @param updateAdminUserPasswordReqVO
     * @return
     */
    Response updatePassword(UpdateAdminUserPasswordReqVO updateAdminUserPasswordReqVO);

    /**
     * 获取当前登录用户信息
     * @return
     */
    Response findUserInfo();
}