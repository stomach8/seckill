package com.lin.seckill.rabbitmq;

import com.lin.seckill.entity.SeckillMessage;
import com.lin.seckill.entity.SeckillOrder;
import com.lin.seckill.entity.User;
import com.lin.seckill.service.IGoodsService;
import com.lin.seckill.service.IOrderService;
import com.lin.seckill.utils.JsonUtil;
import com.lin.seckill.vo.GoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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


//    @RabbitListener(queues = "queue")
//    public void receive(Object msg) {
//        log.info("接收消息" + msg);
//    }
//
//    @RabbitListener(queues = "queue_fanout01")
//    public void fanoutReceive01(Object msg) {
//        log.info("fanout_queue01——接收消息" + msg);
//    }
//
//    @RabbitListener(queues = "queue_fanout02")
//    public void fanoutReceive02(Object msg) {
//        log.info("fanout_queue02——接收消息" + msg);
//    }
//
//    @RabbitListener(queues = "queue_direct01")
//    public void directReceive01(Object msg) {
//        log.info("queue_direct01——接收消息" + msg);
//    }
//
//    @RabbitListener(queues = "queue_direct02")
//    public void directReceive02(Object msg) {
//        log.info("queue_direct02——接收消息" + msg);
//    }
//
//    @RabbitListener(queues = "queue_topic01")
//    public void topicReceive01(Object msg) {
//        log.info("queue_topic01——接收消息" + msg);
//    }
//
//    @RabbitListener(queues = "queue_topic02")
//    public void topicReceive02(Object msg) {
//        log.info("queue_topic02——接收消息" + msg);
//    }

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IOrderService orderService;

    @RabbitListener(queues = "seckillQueue")
    public void receive(String message) {
        log.info("接收消息：" + message);
        SeckillMessage seckillMessage = JsonUtil.jsonStr2Object(message, SeckillMessage.class);
        Long goodId = seckillMessage.getGoodId();
        User user = seckillMessage.getUser();
        GoodsVO goodsVO = goodsService.findGoodsVOByGoodsId(goodId);
        if (goodsVO.getStockCount() < 1) {
            return;
        }
        //判断是否重复抢购
        SeckillOrder seckillOrder = (SeckillOrder) redisTemplate.opsForValue().get("order:" + user.getId() + ":" + goodId);
        if (seckillOrder != null) {
            return;
        }
        //下单
        orderService.seckill(user, goodsVO);
    }
}
