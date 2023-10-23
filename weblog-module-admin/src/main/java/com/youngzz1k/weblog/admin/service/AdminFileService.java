package com.youngzz1k.weblog.admin.service;

import com.youngzz1k.weblog.common.utils.Response;
import org.springframework.web.multipart.MultipartFile;

public interface AdminFileService {

        /**
         * 上传文件
         * @param file
         * @return
         */
        Response uploadFile(MultipartFile file);
}
