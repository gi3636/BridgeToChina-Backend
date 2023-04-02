package com.btchina.message.model.send;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@Data
@ToString
public class ChatMessage implements Serializable {

    @Setter(AccessLevel.NONE)
    @ApiModelProperty("数据类型 1是聊天消息")
    final private Integer dataType = 2;
    @ApiModelProperty("对话ID")
    private String dialogId;
    @ApiModelProperty("发送者ID")
    private String senderId;
    @ApiModelProperty("接收者ID")
    private String receiverId;
    @ApiModelProperty("消息内容")
    private String content;
    @ApiModelProperty("消息ID(前端消息ID)")
    private String msgId;
    @ApiModelProperty("聊天类型 1是私聊 2是群聊")
    private Integer chatType;
    private Integer messageType;

}
