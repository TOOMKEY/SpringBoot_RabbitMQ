package com.Topic_mvc.config;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.*;
/*
                         ---error----->Queue1
                       --
                     --
                    --       ---error---->

     P-----exchange------->  ----warning----->  --------Queue2

                             -----success--->


 */

/**
 * @author TB
 * @date 2019/4/10 - 18:26
 **/
@Component
public class Send {
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public static final String Exchange = "Exchange";
    public void send( MultipartFile file) throws IOException {
        rabbitTemplate.convertAndSend(Exchange,"user.get",file.getBytes());
        System.out.println("生产者生产了一个消息： " + "  " + new Date().getTime());
    }
}