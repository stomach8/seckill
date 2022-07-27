package com.lin.seckill.controller;


import com.lin.seckill.entity.User;
import com.lin.seckill.rabbitmq.MQSender;
import com.lin.seckill.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author star
 * @since 2022-07-12
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private MQSender mqSender;

    @GetMapping("/info")
    @ResponseBody
    public RespBean info(User user) {
        return RespBean.success(user);
    }

//    /**
//     * 测试 mq 消息发送
//     */
//    @GetMapping("/mq")
//    @ResponseBody
//    public void mq() {
//        mqSender.send("hello");
//    }
//
//    /**
//     * fanout 发送消息
//     */
//    @GetMapping("/mq/fanout")
//    @ResponseBody
//    public void fanoutMQ() {
//        mqSender.fanoutSend("hello,fanout!");
//    }
//
//    /**
//     * direct 发送消息
//     */
//    @GetMapping("/mq/direct01")
//    @ResponseBody
//    public void directMQ01() {
//        mqSender.directSend01("hello,direct01!");
//    }
//
//    @GetMapping("/mq/direct02")
//    @ResponseBody
//    public void directMQ02() {
//        mqSender.directSend02("hello,direct02!");
//    }
//
//    @GetMapping("/mq/direct03")
//    @ResponseBody
//    public void directMQ03() {
//        mqSender.directSend03("hello,direct03!");
//    }
//
//    @GetMapping("/mq/topic01")
//    @ResponseBody
//    public void topicMQ01() {
//        mqSender.topicSend01("hello,topic01!");
//    }
//
//    @GetMapping("/mq/topic02")
//    @ResponseBody
//    public void topicMQ02() {
//        mqSender.topicSend02("hello,topic02!");
//    }
}
