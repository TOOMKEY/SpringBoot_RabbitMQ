package tb.simpleQueue;

import com.rabbitmq.client.*;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**接收消息
 * 简单队列耦合度高，生产者和消费者一对一。
 * @author TB
 * @date 2019/4/8 - 18:54
 **/
public class Recivice {
    private static final String QueueName="simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection= RabbitMqConnection.getConnection();
        //获取某个通道
        Channel channel=connection.createChannel();
        //新方法
        DefaultConsumer consumer=new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String out=new String(body,"utf-8");
                System.out.println(out);
            }
        };
        channel.basicConsume(QueueName,true,consumer);
    }

    /*老方法

        //获取队列
        QueueingConsumer queueingConsumer=new QueueingConsumer(channel);
        //监听队列
        channel.basicConsume(QueueName,true,queueingConsumer);
        //
        while(true){
          QueueingConsumer.Delivery d=queueingConsumer.nextDelivery();
          String out=new String(d.getBody());
            System.out.println(out);
        }
   */
}

