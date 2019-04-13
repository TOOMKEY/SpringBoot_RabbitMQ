package tb.tx;

import com.rabbitmq.client.*;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author TB
 * @date 2019/4/10 - 19:42
 **/
public class ReciveTx {
    private static String QueueName="TX send";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection= RabbitMqConnection.getConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QueueName,false,false,false,null);
        DefaultConsumer consumer=new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String out=new String(body,"utf-8");
                System.out.println(out);
                //手动回值
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        Boolean ACK=false;
        channel.basicConsume(QueueName,ACK,consumer);
    }
}
