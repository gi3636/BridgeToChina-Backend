package com.btchina.question.mq;


import com.btchina.question.constant.QuestionConstant;
import com.btchina.question.entity.Question;
import com.btchina.question.model.doc.QuestionDoc;
import com.btchina.question.service.QuestionService;
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

    /**
     * 监听问题更新消息
     *
     * @param questionDoc
     */
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
}
