package tb.Spring_Rabbit;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author TB
 * @date 2019/4/10 - 21:35
 **/
public class SpringMain {
    public static void main(final String ... args) throws InterruptedException {
        //加载容器xml文件
        AbstractApplicationContext context=new ClassPathXmlApplicationContext("classpath:spring_rabbit.xml");
        //RabbitTemplate模板
        RabbitTemplate rabbitTemplate=context.getBean(RabbitTemplate.class);
        //发送消息
        rabbitTemplate.convertAndSend("你好！");
        Thread.sleep(1000);
        //销毁容器
        context.destroy();
    }
}
