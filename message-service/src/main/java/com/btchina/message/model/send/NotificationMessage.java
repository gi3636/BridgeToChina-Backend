package com.btchina.message.model.send;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

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
public class NotificationMessage implements Serializable {

    @Setter(AccessLevel.NONE)
    @ApiModelProperty(" 1是回执消息，2是聊天消息 3是pong消息 4是通知消息")
    final private Integer dataType = 4;

    @ApiModelProperty("id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty("发送者id, 0是系统发送")
    private Long senderId;

    @ApiModelProperty("接收者id")
    private Long receiverId;

    @ApiModelProperty("对象id")
    private Long objectId;

    @ApiModelProperty("操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注")
    private Integer actionType;

    @ApiModelProperty("消息模板id")
    private Integer templateId;

    @ApiModelProperty("渠道类型 1 站内信 2短信 3邮箱")
    private Integer channelType;

    @ApiModelProperty("对象类型 1 问题 2 用户")
    private Integer objectType;

    @ApiModelProperty("是否已读  1已读 0未读 ")
    private Boolean isRead;

    @ApiModelProperty("阅读时间")
    private Date readTime;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

}
