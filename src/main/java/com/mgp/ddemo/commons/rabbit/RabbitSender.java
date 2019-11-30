package com.mgp.ddemo.commons.rabbit;

import com.mgp.ddemo.user.bean.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;


    /*
    Direct Pattern （此模式不需要配置交换机）
    Fanout Pattern ( 类似于广播一样，将消息发送给和他绑定的队列 )
    Topic Pattern ( 绑定交换机时可以做匹配。 #：表示零个或多个单词。*：表示一个单词 )
    Header Pattern ( 带有参数的匹配规则 )
    ————————————————
    版权声明：本文为CSDN博主「慎独-杨」的原创文章，遵循 CC 4.0 BY-SA 版权协议，转载请附上原文出处链接及本声明。
    原文链接：https://blog.csdn.net/annotation_yang/article/details/80918680
     */
    public void send00() {
        for(int i=0;i<10;i++){
           String context = "hi, i am send00  "+i;
           System.out.println("Sender : " + context);
           //往单个queue里面塞，多个监听进行接收
            //use Direct Pattern.  RabbitMQ default, no need exchange.
           //this.rabbitTemplate.convertAndSend(RabbitConstant.QUEUE_ONE, context);

            //往交换机里面塞
            //use Topic Pattern
            this.rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGENEW, RabbitConstant.QUEUE_ONE, context);
            this.rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGENEW, RabbitConstant.QUEUE_TWO, context);
        }
    }

    public void send01() {
        for(int i=0;i<10;i++){
            String context = "hi, i am send01  "+i;
            System.out.println("Sender : " + context);
            this.rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGENEW, "", context);
        }
    }

    public void send1() {
        String context = "hi, i am send1";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGENEW, "QUEUE_#", context);
    }

    public void send2() {
        User user01 = new  User();
        user01.setNickname("mgp");
        user01.setUsername("mm");
        System.out.println("Sender : " + user01.toString());
        this.rabbitTemplate.convertAndSend(RabbitConstant.TOPIC_EXCHANGENEW, RabbitConstant.QUEUE_TWO, user01);
    }

    public void send03() {//订阅模式
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        //向绑定交换机的所有queue发送
        this.rabbitTemplate.convertAndSend(RabbitConstant.FANOUT_EXCHANGE,"", context);
    }

}
