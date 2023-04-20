package com.btchina.message.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class DialogVO {
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("对方用户ID")
    private Long toUserId;

    @ApiModelProperty("对方用户昵称")
    private String toUserNickname;

    @ApiModelProperty("对方用户头像")
    private String toUserAvatar;

    @ApiModelProperty("聊天类型 1是私聊 2是群聊")
    private Integer chatType;

    @ApiModelProperty("消息类型 1是文本 2是图片")
    private Integer messageType;

    @ApiModelProperty("未读消息数")
    private Integer unreadCount;

    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("最后接收消息ID")
    private String lastMsgId;
    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
