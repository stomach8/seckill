package com.lin.seckill;

import com.lin.seckill.entity.User;
import com.lin.seckill.service.IUserService;
import com.lin.seckill.utils.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class SeckillApplicationTests {

    @Autowired
    private IUserService userMapper;

    @Test
    void contextLoads() {
        List<User> users = new ArrayList<>(5000);
        for (int i = 0; i < 5000; i++) {
            User user = new User();
            user.setId(13000000000L + i);
            user.setNickname("user" + i);
            user.setSalt("1a2b3c");
            user.setPassword(MD5Util.inputPassToDBPass("123456", user.getSalt()));
            user.setRegisterDate(new Date());
            user.setLastLoginDate(new Date());
            user.setLoginCount(1);
            user.setHead("nil");
            users.add(user);
        }
        //向数据库插入数据
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            System.out.println(user);
            userMapper.save(user);
        }

    }

}
