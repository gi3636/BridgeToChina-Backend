package com.btchina.message.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class DialogAddForm {
    @ApiModelProperty(value = "对方用户ID", required = true)
    private Long toUserId;

}
