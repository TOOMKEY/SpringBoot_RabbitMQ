package tb.topic;

import com.rabbitmq.client.*;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author TB
 * @date 2019/4/10 - 19:10
 **/
public class Recive2 {
    private static String QueueName="recive2";
    private static String topicName="Queue_Topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection= RabbitMqConnection.getConnection();
        Channel channel=connection.createChannel();
        //消息持久化设置
        Boolean Druble=false;
        channel.queueDeclare(QueueName,Druble,false,false,null);
        channel.queueBind(QueueName,topicName,"student.get");//绑定路由KEY
        channel.queueBind(QueueName,topicName,"student.#");//全匹配
        channel.basicQos(1);
        //新方法
        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String out=new String(body,"utf-8");
                System.out.println(1+out);
                //手动回值
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        Boolean ACK=false;//消息应答设置，默认为true,存在缺点。
        channel.basicConsume(QueueName,ACK,consumer);
    }
}
