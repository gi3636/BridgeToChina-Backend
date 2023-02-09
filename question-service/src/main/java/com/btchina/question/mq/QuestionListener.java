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

    @RabbitListener(queues = QuestionConstant.INSERT_QUEUE_NAME)
    public void listenQuestionInsert(Question question) {
        if (question != null) {
            log.info("监听到新增消息，问题为：{}", question);
            QuestionDoc questionDoc = new QuestionDoc(question);
            questionService.addEsDoc(questionDoc);
        }
    }

    @RabbitListener(queues = QuestionConstant.DELETE_QUEUE_NAME)
    public void listenQuestionDelete(Long id) {
        //questionService.deleteById(id);
    }

    /**
     * 监听问题更新消息
     *
     * @param question
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = QuestionConstant.UPDATE_QUEUE_NAME),
            exchange = @Exchange(name = QuestionConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            key = QuestionConstant.UPDATE_KEY
    ))
    public void listenQuestionUpdate(Question question) {
        if (question != null) {
            QuestionDoc questionDoc = new QuestionDoc(question);
            questionService.updateEsDoc(questionDoc);
        }
    }
}
