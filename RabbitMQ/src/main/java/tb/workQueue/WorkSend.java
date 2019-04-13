package tb.workQueue;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import tb.utils.RabbitMqConnection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
//轮循分发
/** 发送消息
 * @author TB
 * @date 2019/4/8 - 18:46
 **/
//监控界面：http://localhost:15672/#/queues
public class WorkSend {
    private static final String QueueName="work_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
       //获取连接
       Connection connection= RabbitMqConnection.getConnection();
       //获取某个通道
        Channel channel=connection.createChannel();
        //声明一个队列
        channel.queueDeclare(QueueName,false,false,false,null);
        //队列内容信息
        String msg="work rabbitmq";
        for(int i=0;i<50;i++) {
            //发布队列
            channel.basicPublish("", QueueName, null, msg.getBytes());
            //模拟人工发
            Thread.sleep(i*20);
        }
        //关闭流
        channel.close();
        connection.close();


    }
}
