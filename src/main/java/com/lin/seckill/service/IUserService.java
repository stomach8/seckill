package com.lin.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.seckill.entity.User;
import com.lin.seckill.vo.LoginVO;
import com.lin.seckill.vo.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author star
 * @since 2022-07-12
 */
public interface IUserService extends IService<User> {

    RespBean doLogin(LoginVO loginVO);
}
