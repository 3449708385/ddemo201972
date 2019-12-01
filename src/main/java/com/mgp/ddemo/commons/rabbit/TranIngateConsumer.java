/*
package com.mgp.ddemo.commons.rabbit;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = RabbitConstant.TRAN_QUEUE)
public class TranIngateConsumer {

    private static Logger logger = LoggerFactory.getLogger("mq");

    @RabbitHandler
    public void process(String tradePayModelRes, Channel channel, Message message) {
        logger.info("收到支付消息 {}",tradePayModelRes);
        try {
            //do samothing
            logger.info("确认消费进闸支付消息 {}",tradePayModelRes);
        } catch (Exception e) {
            logger.info("进闸支付入库异常 {} - {}",tradePayModelRes,e);
        }
    }
}
*/
