package com.lin.seckill.controller;

import com.lin.seckill.entity.User;
import com.lin.seckill.service.IGoodsService;
import com.lin.seckill.service.IUserService;
import com.lin.seckill.vo.GoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 跳转商品页
     * win10 qps: 1957.8
     * linux qps: 1585
     */
    @RequestMapping(value = "/toList", produces = "text/html;charset=utf-8 ")
    @ResponseBody
    public String toList(Model model, User user, HttpServletRequest request, HttpServletResponse response) {
//        if (StringUtils.isEmpty(ticket)) {
//            return "login";
//        }
//        User user = (User) session.getAttribute(ticket);
//        User user = userService.getUserByCookie(ticket, request, response);
        //Redis中获取页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsList");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        if (user == null) {
            return "login";
        }
        model.addAttribute("user", user);
        model.addAttribute("goodsList", goodsService.findGoodsVO());

        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsList", context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsList", html, 60, TimeUnit.SECONDS);
        }
        return html;
//        return "goodsList";
    }

    @RequestMapping(value = "/toDetail/{goodsId}", produces = "text/html;charset=utf-8 ")
    @ResponseBody
    public String toDetail(Model model, User user, @PathVariable("goodsId") Long goodsId, HttpServletRequest request, HttpServletResponse response) {
        //Redis中获取页面
        ValueOperations valueOperations = redisTemplate.opsForValue();
        String html = (String) valueOperations.get("goodsDetail:" + goodsId);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
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
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goodsDetail", context);
        if (!StringUtils.isEmpty(html)) {
            valueOperations.set("goodsDetail", html, 60, TimeUnit.SECONDS);
        }
        return html;
//        return "goodsDetail";
    }
}
