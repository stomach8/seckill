package com.lin.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.seckill.entity.User;
import com.lin.seckill.mapper.UserMapper;
import com.lin.seckill.service.IUserService;
import com.lin.seckill.utils.MD5Util;
import com.lin.seckill.utils.ValidatorUtil;
import com.lin.seckill.vo.LoginVO;
import com.lin.seckill.vo.RespBean;
import com.lin.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author star
 * @since 2022-07-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public RespBean doLogin(LoginVO loginVO) {
        String mobile = loginVO.getMobile();
        String password = loginVO.getPassword();
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if(!ValidatorUtil.isMobile(mobile)){
            return RespBean.error(RespBeanEnum.MOBILE_ERROR);
        }
        User user = userMapper.selectById(mobile);
        if(null==user){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        if(!MD5Util.formPassToDBPass(password,user.getSalt()).equals(user.getPassword())){
            return RespBean.error(RespBeanEnum.LOGIN_ERROR);
        }
        return RespBean.success();
    }
}
