package com.btchina.message.model.send;

import com.baomidou.mybatisplus.annotation.*;
import com.btchina.message.model.vo.NotifyVO;
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

    @ApiModelProperty("消息通知内容")
    private NotifyVO notifyVO;

}
