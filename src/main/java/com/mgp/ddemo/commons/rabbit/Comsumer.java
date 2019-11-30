package com.mgp.ddemo.commons.rabbit;


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

    @RabbitListener(queues = RabbitConstant.QUEUE_ONE)
    public void process(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("queue_one_receive1: " + new String(message.getBody()));
    }

    @RabbitListener(queues = RabbitConstant.QUEUE_ONE)
    public void process1(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("queue_one_receive2: " + new String(message.getBody()));
    }

    @RabbitListener(queues = RabbitConstant.QUEUE_TWO)
    public void process2(Message message,String user, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        /*ObjectMapper mapper=new ObjectMapper();
        String messaged=new String(message.getBody());
        User user=mapper.readValue(messaged.getBytes("utf-8"),User.class);*/
        log.info("queue_two_receive1: " + new String(message.getBody()));
    }

    @RabbitListener(queues = RabbitConstant.QUEUE_TWO)
    public void process7(Message message,String user, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        /*ObjectMapper mapper=new ObjectMapper();
        String messaged=new String(message.getBody());
        User user=mapper.readValue(messaged.getBytes("utf-8"),User.class);*/
        log.info("queue_two_receive2: " + user.toString());
    }

    @RabbitListener(queues = RabbitConstant.FANOUT_A)
    public void process3(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("fanoutExchange_FANOUT_A_1: " + new String(message.getBody()));
    }

    @RabbitListener(queues = RabbitConstant.FANOUT_B)
    public void process4(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("fanoutExchange_FANOUT_B_1: " + new String(message.getBody()));
    }

    @RabbitListener(queues = RabbitConstant.FANOUT_C)
    public void process5(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("fanoutExchange_FANOUT_C_1: " + new String(message.getBody()));
    }

    @RabbitListener(queues = RabbitConstant.FANOUT_A)
    public void process31(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("fanoutExchange_FANOUT_A_2: " + new String(message.getBody()));
    }

    @RabbitListener(queues = RabbitConstant.FANOUT_B)
    public void process41(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("fanoutExchange_FANOUT_B_2: " + new String(message.getBody()));
    }

    @RabbitListener(queues = RabbitConstant.FANOUT_C)
    public void process51(Message message, Channel channel) throws IOException {
        // 采用手动应答模式, 手动确认应答更为安全稳定
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        log.info("fanoutExchange_FANOUT_C_2: " + new String(message.getBody()));
    }
}
