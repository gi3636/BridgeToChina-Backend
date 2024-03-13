package com.btchina.message.model.form;

import com.btchina.core.api.PageQueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageQueryForm extends PageQueryParam {
    @Schema(description = "对话ID", required = true)
    private Long dialogId;

}
