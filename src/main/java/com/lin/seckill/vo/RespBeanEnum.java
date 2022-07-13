package com.lin.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 * <p>公共返回对象枚举</p>
 *
 * @author : star
 * @date : 2022/7/12 23:24
 */
@ToString
@AllArgsConstructor
@Getter
public enum RespBeanEnum {
    SUCCESS(200,"success"),
    ERROR(500,"服务端异常"),
    LOGIN_ERROR(500210,"用户名或密码错误"),
    MOBILE_ERROR(500211,"手机号码格式错误");

    private final Integer code;
    private final String message;
}
