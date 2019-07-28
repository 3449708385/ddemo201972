package com.mgp.ddemo.commons.rabbit;

import com.mgp.ddemo.user.bean.User;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send00() {
        for(int i=0;i<10;i++){
           String context = "hi, i am send00  "+i;
           System.out.println("Sender : " + context);
           this.rabbitTemplate.convertAndSend("topicExchangenew", "topic_test01", context);
            String context2 = "hi, i am send01  "+i;
            System.out.println("Sender : " + context2);
            this.rabbitTemplate.convertAndSend("topicExchangenew", "topic_test01", context2);
        }
    }

    public void send01() {
        for(int i=0;i<10;i++){
            String context = "hi, i am send01  "+i;
            System.out.println("Sender : " + context);
            this.rabbitTemplate.convertAndSend("topicExchangenew", "topic_test01", context);
        }
    }

    public void send1() {
        String context = "hi, i am send1";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("topicExchangenew", "topic_test01", context);
    }

    public void send2() {
        User user01 = new  User();
        user01.setNickname("mgp");
        user01.setUsername("mm");
        System.out.println("Sender : " + user01.toString());
        this.rabbitTemplate.convertAndSend("topicExchangenew", "topic_test02", user01);
    }

    public void send03() {//订阅模式
        String context = "hi, fanout msg ";
        System.out.println("Sender : " + context);
        this.rabbitTemplate.convertAndSend("fanoutExchange","", context);
    }

}
