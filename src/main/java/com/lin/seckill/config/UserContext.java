package com.lin.seckill.config;

import com.lin.seckill.entity.User;

/**
 * <p>用户信息</p>
 *
 * @author : star
 * @date : 2022/7/30 17:20
 */
public class UserContext {
    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }
}
