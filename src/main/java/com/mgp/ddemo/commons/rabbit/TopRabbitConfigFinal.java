package com.mgp.ddemo.commons.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*这个类主要是创建了一个Topic Exchange通配符 交换机，与两个queue
一个队列QUEUE_ONE以及与交换机TOPIC_EXCHANGENEW的绑定操作、
一个队列QUEUE_TWO以及与交换机TOPIC_EXCHANGENEW进行绑定。*/

@Configuration
public class TopRabbitConfigFinal {

    @Bean
    public Queue topicOne(){
        return new Queue(RabbitConstant.QUEUE_ONE);
    }
    @Bean
    public Queue topicTwo(){
        return new Queue(RabbitConstant.QUEUE_TWO);
    }

    @Bean
    TopicExchange topicExchangenew() {
        return new TopicExchange(RabbitConstant.TOPIC_EXCHANGENEW);
    }

    @Bean
    Binding bindingExchangeMessagenew(Queue topicOne, TopicExchange topicExchangenew) {
        return BindingBuilder.bind(topicOne).to(topicExchangenew).with(RabbitConstant.QUEUE_ONE);
    }

    @Bean
    Binding bindingExchangeMessagesnew(Queue topicTwo, TopicExchange topicExchangenew) {
        return BindingBuilder.bind(topicTwo).to(topicExchangenew).with(RabbitConstant.QUEUE_TWO);
    }
}
