package com.btchina.message.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DialogAddForm {
    @Schema(description = "对方用户ID", required = true)
    private Long toUserId;

}
