package com.Simple_mvc.Config;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;

/**
 * @author TB
 * @date 2019/4/11 - 9:42
 **/
@Component
 public class Sender {

    @Autowired
    AmqpTemplate amqpTemplate;
    public void send( MultipartFile file) throws IOException {
        amqpTemplate.convertAndSend(QueueConfig.QUEUE,file.getBytes());
        System.out.println("生产者生产了一个消息： " + "  " + new Date().getTime());
    }
 }