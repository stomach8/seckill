package com.lin.seckill.controller;


import com.lin.seckill.entity.User;
import com.lin.seckill.service.IOrderService;
import com.lin.seckill.vo.OrderDetailVO;
import com.lin.seckill.vo.RespBean;
import com.lin.seckill.vo.RespBeanEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author star
 * @since 2022-07-15
 */
@RestController
@RequestMapping("/seckill/t-order")
public class OrderController {
    @Autowired
    private IOrderService orderService;


    @RequestMapping("/detail")
    @ResponseBody
    public RespBean detail(User user, Long orderId) {
        if (user == null) {
            return RespBean.error(RespBeanEnum.SESSION_ERROR);
        }
        OrderDetailVO detail = orderService.detail(orderId);
        return RespBean.success(detail);
    }
}
