package com.btchina.message.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class MessageVO {
    @Schema(description ="ID")
    private Long id;

    @Schema(description ="会话ID")
    private Long dialogId;

    @Schema(description ="消息ID(前端消息ID)")
    private String msgId;

    @Schema(description ="发送者ID")
    private Long senderId;

    @Schema(description ="发送者昵称")
    private String senderNickname;

    @Schema(description ="发送者头像")
    private String senderAvatar;

    @Schema(description ="接收者ID")
    private Long receiverId;

    @Schema(description ="接收者昵称")
    private String receiverNickname;

    @Schema(description ="接收者头像")
    private String receiverAvatar;
    @Schema(description ="聊天类型 1是私聊 2是群聊")
    private Integer chatType;

    @Schema(description ="消息类型 1是文本 2是图片 ")
    private Integer messageType;

    @Schema(description ="消息内容")
    private String content;

    @Schema(description ="是否已读 1是已读 , 0 是未读")
    private Boolean isRead;

    @Schema(description ="是否签收 1是已签收 , 0 是未签收")
    private Boolean signed;

    @Schema(description ="扩展")
    private String extend;

    @Schema(description ="创建时间")
    private Date createdTime;

    @Schema(description ="更新时间")
    private Date updatedTime;


}
