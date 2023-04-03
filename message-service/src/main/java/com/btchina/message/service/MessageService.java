package com.btchina.message.service;

import com.btchina.message.entity.Message;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.message.model.form.MessageQueryForm;
import com.btchina.message.model.send.ChatMessage;
import com.btchina.message.model.vo.MessageVO;

import java.util.List;

/**
 * <p>
 * 消息表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
public interface MessageService extends IService<Message> {

    //签收消息
    public void signMessage(String msgId);

    List<MessageVO> query(Long userId, MessageQueryForm messageQueryForm);

    Boolean add(ChatMessage message);

    Boolean read(Long userId, String msgId);
}
