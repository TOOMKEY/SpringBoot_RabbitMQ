package tb.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author TB
 * @date 2019/4/10 - 20:23
 **/
public class Send_Confirm2 {
    private static String QueueName="Confirm";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = RabbitMqConnection.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QueueName, true, false, false, null);
        String mgs = "TX Confirm";
        channel.confirmSelect();//事物开启confirm模式
        //批量操作
        for(int i=0;i<10;i++){
            channel.basicPublish("", QueueName, null, mgs.getBytes());
        }
        //确认
        if(!channel.waitForConfirms()){
            System.out.println("失败");
        }else{
            System.out.println("已提交");
        }
        channel.close();
        connection.close();
    }
}
