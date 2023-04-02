package com.btchina.message.model.form;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageQueryForm extends PageQueryParam {
    @ApiModelProperty(value = "对话ID", required = true)
    private Long dialogId;

}
