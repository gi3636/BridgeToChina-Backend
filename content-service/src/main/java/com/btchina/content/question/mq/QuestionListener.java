package com.btchina.content.mq;


import com.btchina.content.infra.constant.QuestionConstant;
import com.btchina.content.question.model.doc.QuestionDoc;
import com.btchina.content.question.service.QuestionService;
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
public class QuestionListener {

    @Autowired
    QuestionService questionService;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = QuestionConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = QuestionConstant.INSERT_QUEUE_NAME),
            key = {QuestionConstant.INSERT_KEY}
    ))
    public void listenQuestionInsert(QuestionDoc questionDoc) {
        log.info("监听到新增消息，问题为：{}", questionDoc);
        if (questionDoc != null) {
            questionService.addEsDoc(questionDoc);
        }
    }


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = QuestionConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = QuestionConstant.DELETE_QUEUE_NAME),
            key = QuestionConstant.DELETE_KEY
    ))
    public void listenQuestionDelete(Long id) {
        log.info("监听到删除消息，问题id为：{}", id);
        questionService.deleteEsDoc(id);
        //questionService.deleteById(id);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = QuestionConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = QuestionConstant.UPDATE_QUEUE_NAME),
            key = QuestionConstant.UPDATE_KEY
    ))
    public void listenQuestionUpdate(QuestionDoc questionDoc) {
        log.info("监听到更新消息，问题为：{}", questionDoc);
        if (questionDoc != null) {
            questionService.updateEsDoc(questionDoc);
        }
    }


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = QuestionConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = QuestionConstant.INCREASE_FAVOURITE_COUNT_QUEUE_NAME),
            key = QuestionConstant.INCREASE_FAVOURITE_COUNT_ROUTING_KEY
    ))
    public void listenQuestionIncreaseFavouriteCount(Long questionId) {
        log.info("监听到收藏增加，问题为：{}", questionId);
        questionService.increaseFavouriteCount(questionId);
    }

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = QuestionConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = QuestionConstant.DECREASE_FAVOURITE_COUNT_QUEUE_NAME),
            key = QuestionConstant.DECREASE_FAVOURITE_COUNT_ROUTING_KEY
    ))
    public void listenQuestionDecreaseFavouriteCount(Long questionId) {
        log.info("监听到收藏减少，问题为：{}", questionId);
        questionService.decreaseFavouriteCount(questionId);
    }
}
