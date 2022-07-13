package com.lin.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>公共返回对象</p>
 *
 * @author : star
 * @date : 2022/7/12 23:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespBean {
    private long code;
    private String message;
    private Object obj;

    public static RespBean success() {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBeanEnum.SUCCESS.getMessage(),null);
    }

    public static RespBean success(Object obj) {
        return new RespBean(RespBeanEnum.SUCCESS.getCode(),RespBean.success().getMessage(),obj);
    }

    public static RespBean error(RespBeanEnum respBeanEnum) {
        return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(),null);
    }

    public static RespBean error(RespBeanEnum respBeanEnum,Object obj) {
        return new RespBean(respBeanEnum.getCode(),respBeanEnum.getMessage(),obj);
    }
}
