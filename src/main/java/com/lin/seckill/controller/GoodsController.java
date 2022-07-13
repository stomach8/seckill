package com.lin.seckill.controller;

import com.lin.seckill.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * <p>商品控制器</p>
 *
 * @author : star
 * @date : 2022/7/13 23:16
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    /**
     * 跳转商品页
     */
    @RequestMapping("/toList")
    public String toList(HttpSession session, Model model, @CookieValue("userTicket") String ticket) {
        if (StringUtils.isEmpty(ticket)) {
            return "login";
        }
        User user = (User) session.getAttribute(ticket);
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        return "goodsList";
    }
}
