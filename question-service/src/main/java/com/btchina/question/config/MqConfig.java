package com.btchina.question.config;


import com.btchina.question.constant.QuestionConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(QuestionConstant.EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue insertQueue() {
        return new Queue(QuestionConstant.INSERT_QUEUE_NAME, true);
    }

    @Bean
    public Queue deleteQueue() {
        return new Queue(QuestionConstant.DELETE_QUEUE_NAME, true);
    }

    @Bean
    public Binding insertQueueBinding() {
        return BindingBuilder.bind(insertQueue()).to(topicExchange()).with(QuestionConstant.INSERT_KEY);
    }

    @Bean
    public Binding deleteQueueBinding() {
        return BindingBuilder.bind(deleteQueue()).to(topicExchange()).with(QuestionConstant.DELETE_KEY);
    }
}
