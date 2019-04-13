package tb.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import tb.utils.RabbitMqConnection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;
/*
主题模式：归类匹配，借助路由模式。
direct: 如果路由键匹配，消息就投递到对应的队列

fanout：投递消息给所有绑定在当前交换机上面的队列

topic：允许实现有趣的消息通信场景，使得5不同源头的消息能够达到同一个队列。topic队列名称有两个特殊的关键字。

* 可以替换一个单词

# 可以替换所有的单词
 */

/**
 * @author TB
 * @date 2019/4/10 - 19:06
 **/
public class Send {
    private static String topicName="Queue_Topic";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection= RabbitMqConnection.getConnection();
        Channel channel=connection.createChannel();
        channel.exchangeDeclare(topicName,"topic");
        channel.basicQos(1);
        String msg="topic rabbitmq";
        //发布队列
        String RoutKey="student.delete";
        channel.basicPublish(topicName,RoutKey, null, msg.getBytes());
        //关闭流
        channel.close();
        connection.close();
    }
}
