package tb.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

/**
 * @author TB
 * @date 2019/4/10 - 21:03
 **/
public class Deffirent_Send_Confirm {
    private static String QueueName="Confirm";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMqConnection.getConnection();
        Channel channel = connection.createChannel();
        //queueDeclare（名字，是否持久化，独占的queue， 不使用时是否自动删除，其他参数）
        channel.queueDeclare(QueueName, true, false, false, null);
        //开启confirm事物模式
        channel.confirmSelect();
        //未确认的消息集合
        SortedSet<Long> confirmset=Collections.synchronizedSortedSet(new TreeSet<Long>());
        //添加通道监听器
        channel.addConfirmListener(new ConfirmListener() {
            //没有问题的调handleAck
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                if(b){
                    System.out.println("Ack    成功");
                    confirmset.headSet(l+1).clear();
                }else
                {
                    System.out.println("Ack    失败");
                    confirmset.remove(l);
                }
            }
            //有问题的调handleNack
            @Override
            public void handleNack(long l, boolean b) throws IOException {
                if(b){
                    System.out.println("Nack    成功");
                    confirmset.headSet(l+1).clear();
                }else
                {
                    System.out.println("Nack    失败");
                    confirmset.remove(l);
                }
            }
        });
        String msg="asdasdasdas";
        while(true){
            Long seqNO=channel.getNextPublishSeqNo();
            channel.basicPublish("", QueueName, null, msg.getBytes());
            confirmset.add(seqNO);
        }
    }
}
