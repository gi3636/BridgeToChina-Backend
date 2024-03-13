package com.btchina.message.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageReadForm {
    @Schema(description = "前端消息ID", required = true)
    private String msgId;

}
