//package com.lin.seckill.config;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
///**
// * <p>RabbitMQ配置类</p>
// *
// * @author : star
// * @date : 2022/7/24 20:52
// */
//@Configuration
//public class RabbitMQConfig {
//
//    //fanout 模式 队列和交换机的声明
//    private static final String QUEUE01 = "queue_fanout01";
//    private static final String QUEUE02 = "queue_fanout02";
//    private static final String EXCHANGE = "fanoutExchange";
//
//    //direct 模式 队列和交换机以及routingkey的声明
//    private static final String DIRECTQUEUE01 = "queue_direct01";
//    private static final String DIRECTQUEUE02 = "queue_direct02";
//    private static final String DIRECTEXCHANGE = "directExchange";
//    private static final String ROUTINGKEY01 = "queue.red";
//    private static final String ROUTINGKEY02 = "queue.green";
//    private static final String ROUTINGKEY03 = "queue.black";
//
//    //topic 模式
//    private static final String TOPICQUEUE01 = "queue_topic01";
//    private static final String TOPICQUEUE02 = "queue_topic02";
//    private static final String TOPICEXCHANGE = "topicExchange";
//    private static final String ROUTINGKEYTOPIC01 = "#.queue.#";
//    private static final String ROUTINGKEYTOPIC02 = "*.queue.#";
//
//
////    public enum RoutingKey {
////        GREEN("green", "queue.green"),
////        BLACK("black", "queue.black"),
////        ;
////
////        private String name;
////        private String routingKey;
////
////        RoutingKey(String name, String routingKey) {
////            this.name = name;
////            this.routingKey = routingKey;
////        }
////
////        @Override
////        public String toString() {
////            return this.name + "-" + this.routingKey;
////        }
////    }
//
//    /**
//     * 队列模式
//     */
//    @Bean
//    public Queue queue() {
//        return new Queue("queue", true);
//    }
//
//    /**
//     * Fanout模式 （广播）
//     * <hr>
//     */
//    @Bean
//    public Queue queue01() {
//        return new Queue(QUEUE01);
//    }
//
//    @Bean
//    public Queue queue02() {
//        return new Queue(QUEUE02);
//    }
//
//    @Bean
//    public FanoutExchange fanoutExchange() {
//        return new FanoutExchange(EXCHANGE);
//    }
//
//    @Bean
//    public Binding binding01() {
//        return BindingBuilder.bind(queue01()).to(fanoutExchange());
//    }
//
//    @Bean
//    public Binding binding02() {
//        return BindingBuilder.bind(queue02()).to(fanoutExchange());
//    }
//
//    /**
//     * Direct模式
//     * 绑定可以直接在 @RabbitListener 注解中绑定即可，
//     * 并且可以填写队列，交换机等的消息，没必要在这里
//     * <p>@RabbitListener(bindings - @QueueBind(</p>
//     * <p>     value = @Queue(name = "queue_direct01"),</p>
//     * <p>     exchange = @Exchange(name = "directExchange", type = ExchangeTypes.DIRECT),</p>
//     * <p>     key = {"queue.red", "queue.green"}</p>
//     * <p>))</p>
//     * <hr>
//     */
//    @Bean
//    public Queue queue03() {
//        return new Queue(DIRECTQUEUE01);
//    }
//
//    @Bean
//    public Queue queue04() {
//        return new Queue(DIRECTQUEUE02);
//    }
//
//    @Bean
//    public DirectExchange directExchange() {
//        return new DirectExchange(DIRECTEXCHANGE);
//    }
//
//    @Bean
//    public Binding binding03() {
//        return BindingBuilder.bind(queue03()).to(directExchange()).with(ROUTINGKEY01);
//    }
//
//    @Bean
//    public Binding binding04() {
//        return BindingBuilder.bind(queue04()).to(directExchange()).with(ROUTINGKEY02);
//    }
//
//    @Bean
//    public Binding binding05() {
//        return BindingBuilder.bind(queue03()).to(directExchange()).with(ROUTINGKEY03);
//    }
//
//    @Bean
//    public Binding binding06() {
//        return BindingBuilder.bind(queue04()).to(directExchange()).with(ROUTINGKEY03);
//    }
//
//    /**
//     * Topic模式
//     * <hr>
//     */
//    @Bean
//    public Queue queue05() {
//        return new Queue(TOPICQUEUE01);
//    }
//
//    @Bean
//    public Queue queue06() {
//        return new Queue(TOPICQUEUE02);
//    }
//
//    @Bean
//    public TopicExchange topicExchange() {
//        return new TopicExchange(TOPICEXCHANGE);
//    }
//
//    @Bean
//    public Binding binding07() {
//        return BindingBuilder.bind(queue05()).to(topicExchange()).with(ROUTINGKEYTOPIC01);
//    }
//
//    @Bean
//    public Binding binding08() {
//        return BindingBuilder.bind(queue06()).to(topicExchange()).with(ROUTINGKEYTOPIC02);
//    }
//}
