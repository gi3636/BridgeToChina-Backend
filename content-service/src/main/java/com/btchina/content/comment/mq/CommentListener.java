package com.btchina.content.comment.mq;



import com.btchina.content.infra.constant.CommentConstant;
import com.btchina.content.comment.model.entity.Comment;
import com.btchina.content.comment.service.CommentService;
import com.btchina.content.question.feign.QuestionClient;
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
public class CommentListener {

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionClient questionClient;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = CommentConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = CommentConstant.INSERT_QUEUE_NAME),
            key = {CommentConstant.INSERT_KEY}
    ))
    public void listenCommentInsert(Comment comment) {
        log.info("监听到新增消，评论为：{}", comment);
        questionClient.increaseCommentCount(comment.getAnswerId());
    }


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = CommentConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = CommentConstant.DELETE_QUEUE_NAME),
            key = CommentConstant.DELETE_KEY
    ))
    public void listenCommentDelete(Comment comment) {
        log.info("监听到删除消息，评论id为：{}", comment);
        questionClient.decreaseCommentCount(comment.getAnswerId());
    }

}
