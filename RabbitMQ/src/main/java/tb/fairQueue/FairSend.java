package tb.fairQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
//能者多劳分发模式

/** 发送消息
 * @author TB
 * @date 2019/4/8 - 18:46
 **/
//监控界面：http://localhost:15672/#/queues
public class FairSend {
    private static final String QueueName="fiar_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
       //获取连接
       Connection connection= RabbitMqConnection.getConnection();
       //获取某个通道
        Channel channel=connection.createChannel();
        //声明一个队列
        Boolean durable=false;//消息持久化设置
        channel.queueDeclare(QueueName,durable,false,false,null);
        //队列内容信息
        /*
            在未得到消费者确认消息时，只发一个信息给消费者。
         */
        channel.basicQos(1);
        String msg="fair rabbitmq";
        for(int i=0;i<50;i++) {
            //发布队列
            channel.basicPublish("", QueueName, null, msg.getBytes());
        }
        //关闭流
        channel.close();
        connection.close();


    }
}
