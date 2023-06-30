package com.btchina.message.model.send;

import io.swagger.annotations.Api;
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
    @ApiModelProperty(" 1是回执消息，2是聊天消息 3是pong消息 4是通知消息")
    final private Integer dataType = 2;
    @ApiModelProperty("对话ID")
    private Long dialogId;
    @ApiModelProperty("发送者ID")
    private Long senderId;
    @ApiModelProperty("接收者ID")
    private Long receiverId;
    @ApiModelProperty("消息内容")
    private String content;
    @ApiModelProperty("消息ID(前端消息ID)")
    private String msgId;
    @ApiModelProperty("聊天类型 1是私聊 2是群聊")
    private Integer chatType;
    @ApiModelProperty("消息类型 1是文本 2是图片 ")
    private Integer messageType;

}
