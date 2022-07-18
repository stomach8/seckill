package com.lin.seckill.controller;

import com.lin.seckill.entity.User;
import com.lin.seckill.service.IGoodsService;
import com.lin.seckill.service.IUserService;
import com.lin.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

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

    @Autowired
    private IGoodsService goodsService;

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
        model.addAttribute("goodsList", goodsService.findGoodsVO());
        return "goodsList";
    }

    @RequestMapping("/toDetail/{goodsId}")
    public String toDetail(Model model, User user, @PathVariable("goodsId") Long goodsId) {
        model.addAttribute("user", user);
        GoodsVO goodsVO = goodsService.findGoodsVOByGoodsId(goodsId);
        //正常的话这部分应该前端处理，后端只返回状态即可
        //返回状态的话可能在秒杀结束的瞬间，之后用户还是可以进行秒杀，
        //因此更好的做法是返回开始结束时间
        Date startDate = goodsVO.getStartDate();
        Date endDate = goodsVO.getEndDate();
        Date nowDate = new Date();
        //秒杀状态
        int seckillStatus = 0;
        //秒杀倒计时
        int remainSeconds = 0;
        if (nowDate.before(startDate)) {
            remainSeconds = (int) ((startDate.getTime() - nowDate.getTime()) / 1000);
        } else if (nowDate.after(endDate)) {
            seckillStatus = 2;
            remainSeconds = -1;
        } else {
            seckillStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("secKillStatus", seckillStatus);
        model.addAttribute("goods", goodsVO);
        return "goodsDetail";
    }
}
