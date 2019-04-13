package tb.rout;

import com.rabbitmq.client.*;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author TB
 * @date 2019/4/10 - 18:33
 **/
public class Reciview1 {
    private static String QueueName="rout";
    private static String Name="recive1";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection= RabbitMqConnection.getConnection();
        Channel channel=connection.createChannel();
        //消息持久化设置
        Boolean Druble=false;
        channel.queueDeclare(Name,Druble,false,false,null);

        channel.queueBind(Name,QueueName,"error");//绑定路由KEY
        channel.queueBind(Name,QueueName,"success");//绑定路由KEY
        channel.queueBind(Name,QueueName,"warning");//绑定路由KEY
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
        channel.basicConsume(Name,ACK,consumer);
    }
}
