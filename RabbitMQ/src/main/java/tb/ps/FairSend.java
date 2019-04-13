package tb.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
//订阅模式,获取相同资料
/*
             ----c1
   P----x----
             ----c2
 */


/** 发送消息
 * @author TB
 * @date 2019/4/8 - 18:46
 **/
//监控界面：http://localhost:15672/#/queues
public class FairSend {
    private static final String QueueName_exanchge="ps_queue_exchange";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
       //获取连接
       Connection connection= RabbitMqConnection.getConnection();
       //获取某个通道
        Channel channel=connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(QueueName_exanchge,"fanout");//第二参数表示不路由
        //队列内容信息
        /*
            在未得到消费者确认消息时，只发一个信息给消费者。
         */
        channel.basicQos(1);
        String msg="ps rabbitmq";
        for(int i=0;i<50;i++) {
            //发布队列
            channel.basicPublish(QueueName_exanchge,"", null, msg.getBytes());
        }
        //关闭流
        channel.close();
        connection.close();
    }
}
