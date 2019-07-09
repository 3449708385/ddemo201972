package com.mgp.ddemo.commons.util;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TopRabbitConfigFinal {

    final static String message01 = "topic_test01";
    final static String message02 = "topic_test02";

    @Bean
    public Queue topicTest01(){
        return new Queue(TopRabbitConfigFinal.message01);
    }
    @Bean
    public Queue topicTest02(){
        return new Queue(TopRabbitConfigFinal.message02);
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
