package com.Topic_mvc.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TB
 * @date 2019/4/13 - 13:02
 **/
@Configuration
public class RabbitMqConfig {
    public static final String routingKey1 = "user.add";
    public static final String routingKey2 = "user.get";
    public static final String routingKey3 = "user.delete";
    public static final String queueName1 = "queue1";
    public static final String queueName2 = "queue2";
    public static final String Exchange = "Exchange";
    @Bean
    TopicExchange exchange() {
        return new TopicExchange(Exchange);
    }
    @Bean
    public Queue queue1() {
        return new Queue(queueName1, true);

    }
    @Bean
    public Queue queue2() {
        return new Queue(queueName2, true);

    }
    @Bean
    Binding binding1(Queue queue1, TopicExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with(routingKey1);
    }
    @Bean
    Binding binding2(Queue queue1, TopicExchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with(routingKey2);
    }
    @Bean
    Binding binding3(Queue queue2, TopicExchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange).with(routingKey3);
    }
}
