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
public class PongMessage implements Serializable {

    @Setter(AccessLevel.NONE)
    @Schema(description =" 1是回执消息，2是聊天消息 3是pong消息 4是通知消息")
    final private Integer dataType = 3;
    @Schema(description ="消息内容")
    private String content;

}
