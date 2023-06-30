package com.btchina.message.model.send;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
public class AckMessage implements Serializable {

    @Setter(AccessLevel.NONE)
    @ApiModelProperty(" 1是回执消息，2是聊天消息 3是pong消息 4是通知消息")
    final private Integer dataType = 1;

    @ApiModelProperty("消息ID(前端消息ID)")
    private String msgId;

    @ApiModelProperty("发送者ID")
    private Long senderId;

    @ApiModelProperty("接收者ID")
    private Long receiverId;

}
