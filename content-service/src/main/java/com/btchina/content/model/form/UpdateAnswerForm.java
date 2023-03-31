package com.btchina.content.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateAnswerForm {

    @ApiModelProperty("回答id")
    private Long id;

    @ApiModelProperty("回答内容")
    private String content;

}
