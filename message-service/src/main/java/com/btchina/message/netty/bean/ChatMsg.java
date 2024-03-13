package com.btchina.message.netty.bean;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatMsg implements Serializable {
    @Schema(description ="对话ID")
    private Long dialogId;
    @Schema(description ="发送者ID")
    private Long senderId;
    @Schema(description ="接收者ID")
    private Long receiverId;
    @Schema(description ="消息内容")
    private String content;
    @Schema(description ="消息ID(前端消息ID)")
    private String msgId;
    @Schema(description ="聊天类型 1是私聊 2是群聊")
    private Integer chatType;
    @Schema(description ="消息类型 1是文本 2是图片 ")
    private Integer messageType;
}
