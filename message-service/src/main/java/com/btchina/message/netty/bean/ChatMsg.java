package com.btchina.message.netty.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsg implements Serializable {
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
    @ApiModelProperty("消息类型 1是文本 2是图片 ")
    private Integer messageType;
}
