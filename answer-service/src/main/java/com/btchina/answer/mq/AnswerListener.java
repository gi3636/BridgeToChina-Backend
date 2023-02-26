package com.btchina.answer.mq;


import com.btchina.answer.constant.AnswerConstant;
import com.btchina.feign.clients.QuestionClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AnswerListener {

    @Autowired
    private QuestionClient questionClient;


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = AnswerConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = AnswerConstant.INCREASE_QUEUE_NAME),
            key = {AnswerConstant.INCREASE_ROUTING_KEY}
    ))
    public void listenAnswerUseIncrease(Long question) {
        log.info("监听到回答数增加，question:{}", question);
        questionClient.increaseAnswerCount(question);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = AnswerConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = AnswerConstant.DECREASE_QUEUE_NAME),
            key = {AnswerConstant.DECREASE_ROUTING_KEY}
    ))
    public void listenAnswerUseDecrease(Long question) {
        log.info("监听到回答数减少，question:{}", question);
        questionClient.increaseAnswerCount(question);
    }


}
