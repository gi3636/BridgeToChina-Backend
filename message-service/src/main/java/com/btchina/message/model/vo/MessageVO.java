package com.btchina.message.model.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class MessageVO {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("会话ID")
    private Long dialogId;

    @ApiModelProperty("消息ID(前端消息ID)")
    private String msgId;

    @ApiModelProperty("发送者ID")
    private Long senderId;

    @ApiModelProperty("发送者昵称")
    private String senderNickname;

    @ApiModelProperty("发送者头像")
    private String senderAvatar;

    @ApiModelProperty("接收者ID")
    private Long receiverId;

    @ApiModelProperty("接收者昵称")
    private String receiverNickname;

    @ApiModelProperty("接收者头像")
    private String receiverAvatar;
    @ApiModelProperty("聊天类型 1是私聊 2是群聊")
    private Integer chatType;

    @ApiModelProperty("消息类型 1是文本 2是图片 ")
    private Integer messageType;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("是否已读 1是已读 , 0 是未读")
    private Boolean isRead;

    @ApiModelProperty("是否签收 1是已签收 , 0 是未签收")
    private Boolean signed;

    @ApiModelProperty("扩展")
    private String extend;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    private Date updatedTime;


}
