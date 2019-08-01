package com.mgp.ddemo.commons.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopRabbitConfigFinal {

    final static String MESSAGE_ONE = "topic_test01";
    final static String MESSAGE_TWO = "topic_test02";

    @Bean
    public Queue topicTest01(){
        return new Queue(TopRabbitConfigFinal.MESSAGE_ONE);
    }
    @Bean
    public Queue topicTest02(){
        return new Queue(TopRabbitConfigFinal.MESSAGE_TWO);
    }

    @Bean
    TopicExchange topicExchangenew() {
        return new TopicExchange("topicExchangenew");
    }

    @Bean
    Binding bindingExchangeMessagenew(Queue topicTest01, TopicExchange topicExchangenew) {
        return BindingBuilder.bind(topicTest01).to(topicExchangenew).with("topic_test01");
    }

    @Bean
    Binding bindingExchangeMessagesnew(Queue topicTest02, TopicExchange topicExchangenew) {
        return BindingBuilder.bind(topicTest02).to(topicExchangenew).with("topic_test02");
    }
}
