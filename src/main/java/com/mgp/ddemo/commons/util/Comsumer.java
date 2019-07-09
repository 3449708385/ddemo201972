package com.mgp.ddemo.commons.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgp.ddemo.user.bean.User;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Comsumer {
    private Logger log = LoggerFactory.getLogger(Comsumer.class);

    @RabbitListener(queues = "topic_test01")
    public void process(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("test01_receive1: " + new String(message.getBody()));
    }

    @RabbitListener(queues = "topic_test01")
    public void process1(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("test01_receive2: " + new String(message.getBody()));
    }

    @RabbitListener(queues = "topic_test02")
    public void process2(Message message,User user, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        /*ObjectMapper mapper=new ObjectMapper();
        String messaged=new String(message.getBody());
        User user=mapper.readValue(messaged.getBytes("utf-8"),User.class);*/
        log.info("test02_receive1: " + user.toString());
    }

    @RabbitListener(queues = "fanout.A")
    public void process3(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("fanoutExchange: " + new String(message.getBody()));
    }

    @RabbitListener(queues = "fanout.B")
    public void process4(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("fanoutExchange: " + new String(message.getBody()));
    }

    @RabbitListener(queues = "fanout.C")
    public void process5(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("fanoutExchange: " + new String(message.getBody()));
    }
}
