package tb.tx;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import tb.utils.RabbitMqConnection;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
/*
    事物机制
    此方式耗时
 */
/**
 * @author TB
 * @date 2019/4/10 - 19:33
 **/
public class SendTx {
    private static String QueueName="TX send";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection= RabbitMqConnection.getConnection();
        Channel channel=connection.createChannel();
        channel.queueDeclare(QueueName,false,false,false,null);
        channel.basicQos(1);
        String mgs="TX SEND";
        try {
            channel.txSelect();//事物开启
            channel.basicPublish("",QueueName , null, mgs.getBytes());
            channel.txCommit();//事物提交
            System.out.println("已提交");
        }catch (IOException e){
            channel.txRollback();//事务回滚
            System.out.println("回滚");
        }
        channel.close();
        connection.close();

    }
}
