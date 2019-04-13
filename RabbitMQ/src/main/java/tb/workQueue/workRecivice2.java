package tb.workQueue;

import com.rabbitmq.client.*;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
//两者接受消息虽然速度不同，但接受数量均相同
/**接收消息
 * 工作队列耦合度低，生产者和消费者一对多。
 * @author TB
 * @date 2019/4/8 - 18:54
 **/
public class workRecivice2 {
    private static final String QueueName="work_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection= RabbitMqConnection.getConnection();
        //获取某个通道
        Channel channel=connection.createChannel();
        //新方法
        DefaultConsumer consumer=new DefaultConsumer(channel){
            int i=0;
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String out=new String(body,"utf-8");
                System.out.println(2+out+"  "+i);
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Boolean ACK=true;
        channel.basicConsume(QueueName,ACK,consumer);
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

