package com.lin.seckill.vo;

import com.lin.seckill.validator.IsMobile;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * <p>登录参数</p>
 *
 * @author : star
 * @date : 2022/7/12 23:46
 */
@Data
public class LoginVO {
    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
