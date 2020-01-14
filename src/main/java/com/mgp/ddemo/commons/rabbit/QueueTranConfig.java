package com.mgp.ddemo.commons.rabbit;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueueTranConfig {

    private String exchange=RabbitConstant.TRAN_EXCHANGE;

    private String ingateQueue=RabbitConstant.TRAN_QUEUE;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange,true,false);
    }

    //queue 构造参数，1 队列名称 2 持久化 3 独占队列（该队列将仅由声明者使用连接） 4 自动删除 5 map 拓展参数
    @Bean
    public Queue ingateQueue() {
        return new Queue(ingateQueue,true,false, false);
    }

    @Bean
    public Binding ingateQueueBinding() {
        return BindingBuilder.bind(ingateQueue()).to(exchange()).with(RabbitConstant.TRAN_QUEUE);
    }

    /**
     * 配置启用rabbitmq事务
     * @param connectionFactory
     * @return
     */
    @Bean("rabbitTransactionManager")
    public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }
}
