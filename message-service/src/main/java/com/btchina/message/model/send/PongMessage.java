package com.btchina.message.model.send;

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
public class PongMessage implements Serializable {

    @Setter(AccessLevel.NONE)
    @ApiModelProperty("数据类型 1是回执消息，2是聊天消息 3是pong消息")
    final private Integer dataType = 3;
    @ApiModelProperty("消息内容")
    private String content;

}
