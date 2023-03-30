package com.btchina.message.service.impl;

import com.btchina.message.entity.Message;
import com.btchina.message.mapper.MessageMapper;
import com.btchina.message.service.MessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

}
