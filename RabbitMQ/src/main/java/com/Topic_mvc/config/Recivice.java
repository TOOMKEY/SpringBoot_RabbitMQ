package com.Topic_mvc.config;

import com.Simple_mvc.utils.ExcelUtil;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**接收消息
 * 公平队列耦合度低，生产者和消费者一对多。
 * @author TB
 * @date 2019/4/8 - 18:54
 **/
@Component
public class Recivice {
    public static final String queueName1 = "queue1";
    public static final String queueName2 = "queue2";
    @RabbitListener(queues =queueName1)
        public void receive1(byte [] ms) throws Exception {
            InputStream sbs = new ByteArrayInputStream(ms);
            List<List<Object>> list1=new ExcelUtil().getBankListByExcel(sbs,".xls");
            for (List li:list1) {
                System.out.println(li);
            }
            System.out.println("消费者1收到了一个消息: " + new Date().getTime());
        }
    @RabbitListener(queues =queueName2)
    public void receive2(byte [] ms) throws Exception {
        InputStream sbs = new ByteArrayInputStream(ms);
        List<List<Object>> list1=new ExcelUtil().getBankListByExcel(sbs,".xls");
        for (List li:list1) {
            System.out.println(li);
        }
        System.out.println("消费者2收到了一个消息: " + new Date().getTime());
    }
}

