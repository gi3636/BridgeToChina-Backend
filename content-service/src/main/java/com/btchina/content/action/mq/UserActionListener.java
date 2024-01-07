package com.btchina.content.action.mq;



import com.btchina.user.constant.UserActionConstant;
import com.btchina.feign.model.userAction.qo.UserActionForm;
import com.btchina.content.action.service.UserActionService;
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
public class UserActionListener {

    @Autowired
    UserActionService userActionService;
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = UserActionConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = UserActionConstant.INSERT_QUEUE_NAME),
            key = {UserActionConstant.INSERT_KEY}
    ))
    public void listenUserActionInsert(UserActionForm userActionForm) {
        log.info("监听到新增用户操作记录，用户操作记录为：{}", userActionForm);
        if (userActionForm != null){
            userActionService.add(userActionForm);
        }
    }


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = UserActionConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = UserActionConstant.DELETE_QUEUE_NAME),
            key = UserActionConstant.DELETE_KEY
    ))
    public void listenUserActionDelete(UserActionForm userActionForm) {
        log.info("监听到删除用户操作记录，用户操作记录为：{}", userActionForm);
        if (userActionForm != null){
            userActionService.delete(userActionForm);
        }
    }

}
