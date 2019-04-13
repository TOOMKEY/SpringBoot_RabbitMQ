package tb.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
//参看borker消息确认机制图
/*

(注意一点，已经在transaction事务模式的channel是不能再设置成confirm模式的，
即这两种模式是不能共存的)
 当消息发送到broker的时候，会执行监听的回调函数，
 其中deliveryTag是消息id（在同一个channel中这个数值是递增的，
 而multiple表示是否批量确认消息。
在生产端要维护一个消息发送的表，消息发送的时候记录消息id，
在消息成功落地broker磁盘并且进行回调确认（ack）的时候，
根据本地消息表和回调确认的消息id进行对比，
这样可以确保生产端的消息表中的没有进行回调确认（或者回调确认时网络问题）的消息进行补救式的重发，
当然不可避免的就会在消息端可能会造成消息的重复消息。
针对消费端重复消息，在消费端进行幂等处理。
（丢消息和重复消息是不可避免的二个极端，比起丢消息，重复消息还有补救措施，
而消息丢失就真的丢失了。
 */
/**
 * @author TB
 * @date 2019/4/10 - 20:23
 **/
public class Send_Confirm1 {
    private static String QueueName="Confirm";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitMqConnection.getConnection();
        Channel channel = connection.createChannel();
        //queueDeclare（名字，是否持久化，独占的queue， 不使用时是否自动删除，其他参数）
        channel.queueDeclare(QueueName, true, false, false, null);
        String mgs = "TX Confirm";
        channel.confirmSelect();//事物开启confirm模式
        channel.basicPublish("", QueueName, null, mgs.getBytes());
        if(!channel.waitForConfirms()){
            System.out.println("失败");
        }else{
            System.out.println("成功");
        }
        channel.close();
        connection.close();
    }
}
