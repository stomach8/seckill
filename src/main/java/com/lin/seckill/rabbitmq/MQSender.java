package com.lin.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>消息发送者</p>
 *
 * @author : star
 * @date : 2022/7/24 20:56
 */
@Service
@Slf4j
public class MQSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送秒杀消息
     *
     * @param message
     */
    public void sendSeckillMessage(String message) {
        log.info("发送消息：" + message);
        rabbitTemplate.convertAndSend("seckillExchange", "seckill.message", message);
    }


//    public void send(Object msg) {
//        log.info("发送消息" + msg);
//        rabbitTemplate.convertAndSend("queue", msg);
//    }
//
//    /**
//     * fanout 模式
//     */
//    public void fanoutSend(Object msg) {
//        log.info("fanout——发送消息" + msg);
//        rabbitTemplate.convertAndSend("fanoutExchange", "", msg);
//    }
//
//    /**
//     * direct 模式
//     */
//    public void directSend01(Object msg) {
//        log.info("direct——发送 red 消息" + msg);
//        rabbitTemplate.convertAndSend("directExchange", "queue.red", msg);
//    }
//
//    public void directSend02(Object msg) {
//        log.info("direct——发送 green 消息" + msg);
//        rabbitTemplate.convertAndSend("directExchange", "queue.green", msg);
//    }
//
//    public void directSend03(Object msg) {
//        log.info("direct——发送 black 消息" + msg);
//        rabbitTemplate.convertAndSend("directExchange", "queue.black", msg);
//    }
//
//    /**
//     * topic 模式
//     */
//    public void topicSend01(Object msg) {
//        log.info("topic——发送消息" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "queue.red.message", msg);
//    }
//
//    public void topicSend02(Object msg) {
//        log.info("topic——发送消息" + msg);
//        rabbitTemplate.convertAndSend("topicExchange", "message.queue.green.abc", msg);
//    }
}
