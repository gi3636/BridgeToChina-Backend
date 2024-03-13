package com.btchina.message.model.send;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description =" 1是回执消息，2是聊天消息 3是pong消息 4是通知消息")
    final private Integer dataType = 2;
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
