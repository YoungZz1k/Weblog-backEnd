package com.youngzz1k.weblog.web.controller;


import com.youngzz1k.weblog.common.aspect.ApiOperationLog;
import com.youngzz1k.weblog.common.utils.JsonUtil;
import com.youngzz1k.weblog.common.utils.Response;
import com.youngzz1k.weblog.web.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@RestController
@Slf4j
@Api(tags = "首页模块")
public class TestController {


    @PostMapping("/admin/test")
    @ApiOperationLog(description = "测试接口")
    @ApiOperation(value = "测试接口")
    public Response test(@RequestBody @Validated User user){
        // 打印入参
        log.info(JsonUtil.toJsonString(user));
        user.setCreateTime(LocalDateTime.now());
        user.setTime(LocalTime.now());
        user.setUpdateDate(LocalDate.now());

        return Response.success(user);
    }
}
