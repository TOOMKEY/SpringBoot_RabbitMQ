package com.Simple_mvc.Config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author TB
 * @date 2019/4/11 - 10:41
 **/
@Configuration
public class QueueConfig {

    public static final String QUEUE = "queue";
    @Bean
    public Queue queue() {
             return new Queue(QUEUE, true);
    }
 }