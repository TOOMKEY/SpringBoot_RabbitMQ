package tb.ps;

import com.rabbitmq.client.*;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**接收消息
 * 公平队列耦合度低，生产者和消费者一对多。
 * @author TB
 * @date 2019/4/8 - 18:54
 **/
public class FairRecivice1 {
    private static final String QueueName="ps_queue_iphone";
    private static final String QueueName_exanchge="ps_queue_exchange";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection= RabbitMqConnection.getConnection();
        //获取某个通道
        Channel channel=connection.createChannel();
//
        //声明一个队列
        Boolean durable=false;//消息持久化设置
        channel.queueDeclare(QueueName,durable,false,false,null);
        //绑定交换机
        channel.queueBind(QueueName,QueueName_exanchge,"");
//
        //接收一个
        channel.basicQos(1);
        //新方法
        DefaultConsumer consumer=new DefaultConsumer(channel){
            int i=0;
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String out=new String(body,"utf-8");
                System.out.println(1+out+"   "+i);
                i++;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    //手动回值
                    channel.basicAck(envelope.getDeliveryTag(),false);
                }
            }
        };
        Boolean ACK=false;//消息应答设置，默认为true,存在缺点。
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

