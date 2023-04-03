package com.btchina.user.mq;



import com.btchina.user.constant.UserActionConstant;
import com.btchina.user.model.form.UserActionAddForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserActionListener {

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(name = UserActionConstant.EXCHANGE_NAME, type = ExchangeTypes.TOPIC),
            value = @Queue(name = UserActionConstant.INSERT_QUEUE_NAME),
            key = {UserActionConstant.INSERT_KEY}
    ))
    public void listenUserActionInsert(UserActionAddForm userActionAddForm) {
      //TODO 添加用户操作记录
    }


}
