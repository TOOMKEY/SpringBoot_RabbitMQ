package tb.rout;

import com.rabbitmq.client.*;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author TB
 * @date 2019/4/10 - 18:33
 **/
public class Reciview2 {
    private static String QueueName="rout";
    private static String Name="recive2";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection= RabbitMqConnection.getConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(Name,false,false,false,null);
        channel.queueBind(Name,QueueName,"error");
        channel.basicQos(1);
        //新方法
        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String out=new String(body,"utf-8");
                System.out.println(2+out);
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        Boolean ACK=false;//消息应答设置，默认为true,存在缺点。
        channel.basicConsume(Name,ACK,consumer);
    }
}
