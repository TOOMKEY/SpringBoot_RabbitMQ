package tb.rout;

import com.rabbitmq.client.*;
import tb.utils.RabbitMqConnection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
/*
                         ---error----->Queue1
                       --
                     --
                    --       ---error---->

     P-----exchange------->  ----warning----->  --------Queue2

                             -----success--->


 */
/**
 * @author TB
 * @date 2019/4/10 - 18:26
 **/
public class Send {
    private static String QueueName="rout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection= RabbitMqConnection.getConnection();
        Channel channel=connection.createChannel();
        channel.exchangeDeclare(QueueName,"direct");//路由模式
        channel.basicQos(1);
        String msg="rout rabbitmq";
        //发布队列
        String RoutKey="warning";
        channel.basicPublish(QueueName,RoutKey, null, msg.getBytes());
        //关闭流
        channel.close();
        connection.close();
    }


}
