package tb.simpleQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
//参看图片
/** 发送消息
 * @author TB
 * @date 2019/4/8 - 18:46
 **/
//监控界面：http://localhost:15672/#/queues
public class Send {
    private static final String QueueName="simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
       //获取连接
       Connection connection= RabbitMqConnection.getConnection();
       //获取某个通道
        Channel channel=connection.createChannel();
        //声明一个队列
        channel.queueDeclare(QueueName,false,false,false,null);
        //队列内容信息
        String msg="simple rabbitmq";
        //发布队列
        channel.basicPublish("",QueueName,null,msg.getBytes());
        //关闭流
        channel.close();
        connection.close();


    }
}
