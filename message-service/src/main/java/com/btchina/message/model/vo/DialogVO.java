package com.btchina.message.model.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class DialogVO {
    @Schema(description ="ID")
    private Long id;

    @Schema(description ="对方用户ID")
    private Long toUserId;

    @Schema(description ="对方用户昵称")
    private String toUserNickname;

    @Schema(description ="对方用户头像")
    private String toUserAvatar;

    @Schema(description ="对话ID")
    private Long dialogId;

    @Schema(description ="聊天类型 1是私聊 2是群聊")
    private Integer chatType;

    @Schema(description ="消息类型 1是文本 2是图片")
    private Integer messageType;

    @Schema(description ="未读消息数")
    private Integer unreadCount;

    @Schema(description ="消息内容")
    private String content;

    @Schema(description ="最后接收消息ID")
    private String lastMsgId;
    @Schema(description ="创建时间")
    private Date createdTime;

    @Schema(description ="更新时间")
    private Date updatedTime;

}
