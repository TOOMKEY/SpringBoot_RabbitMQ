package tb.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;


/**获取MQ的连接
 * @author TB
 * @date 2019/4/8 - 18:35
 **/
public class RabbitMqConnection {
    public static Connection getConnection() throws IOException, TimeoutException {
        //创建连接工场
        ConnectionFactory factory=new ConnectionFactory();
        //设置服务地址
        factory.setHost("127.0.0.1");
        //AMQP 5672通信协议
        factory.setPort(5672);
        //vhost
        factory.setVirtualHost("/tb");
        //用户名
        factory.setUsername("tb");
        //密码
        factory.setPassword("216918");

        return factory.newConnection();
    }
}
