package com.lin.seckill.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author star
 * @since 2022-07-12
 */
@Data
@Accessors(chain = true)
@TableName("t_user")
public class User {

    private static final long serialVersionUID = 1L;

    private String nickname;

    /**
     * md5(md5(pass明文+固定salt) + salt)
     */
    private String password;

    private String salt;

    /**
     * 头像
     */
    private String head;

    /**
     * 注册时间
     */
    private LocalDateTime registerDate;

    /**
     * 最后一次登录时间
     */
    private LocalDateTime lastLoginDate;

    /**
     * 登录次数
     */
    private Integer loginCount;


}
