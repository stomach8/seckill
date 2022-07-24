package com.lin.seckill.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * <p>消息接收者</p>
 *
 * @author : star
 * @date : 2022/7/24 20:58
 */
@Service
@Slf4j
public class MQReceiver {

    @RabbitListener(queues = "queue")
    public void receive(Object msg) {
        log.info("接收消息" + msg);
    }

    @RabbitListener(queues = "queue_fanout01")
    public void fanoutReceive01(Object msg) {
        log.info("fanout_queue01——接收消息" + msg);
    }

    @RabbitListener(queues = "queue_fanout02")
    public void fanoutReceive02(Object msg) {
        log.info("fanout_queue02——接收消息" + msg);
    }

    @RabbitListener(queues = "queue_direct01")
    public void directReceive01(Object msg) {
        log.info("queue_direct01——接收消息" + msg);
    }

    @RabbitListener(queues = "queue_direct02")
    public void directReceive02(Object msg) {
        log.info("queue_direct02——接收消息" + msg);
    }

    @RabbitListener(queues = "queue_topic01")
    public void topicReceive01(Object msg) {
        log.info("queue_topic01——接收消息" + msg);
    }

    @RabbitListener(queues = "queue_topic02")
    public void topicReceive02(Object msg) {
        log.info("queue_topic02——接收消息" + msg);
    }
}
