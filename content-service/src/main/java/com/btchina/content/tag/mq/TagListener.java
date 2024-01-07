package com.btchina.content.tag.mq;


import com.btchina.content.tag.model.doc.TagDoc;
import com.btchina.content.tag.service.TagService;
import com.btchina.content.infra.constant.TagConstant;
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
public class TagListener {

    @Autowired
    TagService tagService;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = TagConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = TagConstant.INSERT_QUEUE_NAME),
            key = {TagConstant.INSERT_KEY}
    ))
    public void listenTagInsert(TagDoc tagDoc) {
        log.info("监听到新增消息，标签为：{}", tagDoc);
        if (tagDoc != null) {
            tagService.addEsDoc(tagDoc);
        }
    }


    /**
     * 监听标签更新消息
     *
     * @param tagDoc
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = TagConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = TagConstant.UPDATE_QUEUE_NAME),
            key = TagConstant.UPDATE_KEY
    ))
    public void listenTagUpdate(TagDoc tagDoc) {
        log.info("监听到更新消息，标签为：{}", tagDoc);
        if (tagDoc != null) {
            tagService.updateEsDoc(tagDoc);
        }
    }
}
