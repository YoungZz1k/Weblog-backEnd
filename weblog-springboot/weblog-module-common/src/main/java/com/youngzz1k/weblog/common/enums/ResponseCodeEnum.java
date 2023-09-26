package com.youngzz1k.weblog.common.enums;


import com.youngzz1k.weblog.common.exception.BaseExceptionInterface;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应异常码
 */
@Getter
@AllArgsConstructor
public enum ResponseCodeEnum implements BaseExceptionInterface {

    //通用异常状态码
    SYSTEM_ERROR("10000","出错啦，后台正在努力修复中..."),

    PRODUCT_NOT_FOUND("20000","该产品不存在..."),

    PARAM_NOT_VALID("10001","参数错误..."),

    LOGIN_FAIL("20000", "登录失败"),

    USERNAME_OR_PWD_ERROR("20001", "用户名或密码错误"),
    ;

    //异常码
    private String errorCode;

    //错误信息
    private String errorMessage;
}
