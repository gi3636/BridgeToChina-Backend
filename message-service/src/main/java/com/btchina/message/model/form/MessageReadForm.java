package com.btchina.message.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageReadForm {
    @ApiModelProperty(value = "前端消息ID", required = true)
    private String msgId;

}
