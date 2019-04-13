package com.Simple_mvc.Config;
import com.Simple_mvc.utils.ExcelUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;


/**
 * @author TB
 * @date 2019/4/11 - 9:47
 **/
@Component
public class Receiver {
    @RabbitListener(queues = QueueConfig.QUEUE)
     public void receive(byte[] map) throws Exception {
        InputStream sbs = new ByteArrayInputStream(map);
        List<List<Object>> list=new ExcelUtil().getBankListByExcel(sbs,"Teacher.xls");
        for (List li:list) {
            System.out.println(li);
        }
        System.out.println("消费者收到了一个消息: " + new Date().getTime());
    }

}