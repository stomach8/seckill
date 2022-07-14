package com.lin.seckill.controller;

import com.lin.seckill.entity.User;
import com.lin.seckill.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>商品控制器</p>
 *
 * @author : star
 * @date : 2022/7/13 23:16
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private IUserService userService;

    /**
     * 跳转商品页
     */
    @RequestMapping("/toList")
    public String toList(Model model, User user) {
//        if (StringUtils.isEmpty(ticket)) {
//            return "login";
//        }
////        User user = (User) session.getAttribute(ticket);
//        User user = userService.getUserByCookie(ticket, request, response);
//        if (user == null) {
//            return "login";
//        }
        model.addAttribute("user", user);
        return "goodsList";
    }
}
