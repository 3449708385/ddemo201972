package com.mgp.ddemo.commons.rabbit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * RabbitMQ消息发送类
 */
@Component
public class TranRabbitSender implements RabbitTemplate.ReturnCallback {

    private static final Logger logger = LoggerFactory.getLogger("mq");

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private String exchange=RabbitConstant.TRAN_EXCHANGE;

    private String ingateQueue=RabbitConstant.TRAN_QUEUE;

    /*
    PostConstruct注释用于在完成依赖项注入以执行任何初始化之后需要执行的方法。必须在类投入使用之前调用此方法。
    所有支持依赖注入的类都必须支持此注释。即使类没有请求注入任何资源，也必须调用使用PostConstruct注释的方法。
    只有一个方法可以使用此批注进行批注。
     */
    @PostConstruct
    private void init() {
        //启用事务模式,不能开确认回调
        //rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    @Transactional(rollbackFor = Exception.class,transactionManager = "rabbitTransactionManager")
    public void sendIngateQueue(String msg) {
        logger.info("进闸消息已发送 {}",msg);
        rabbitTemplate.convertAndSend(exchange,ingateQueue,msg);
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText,String exchange, String routingKey) {
        logger.info("消息被退回 {}" , message.toString());
    }

}
