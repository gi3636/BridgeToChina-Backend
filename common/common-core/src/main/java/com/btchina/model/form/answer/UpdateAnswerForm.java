package com.btchina.model.form.answer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class UpdateAnswerForm {

    @ApiModelProperty("回答id")
    private Long id;

    @ApiModelProperty("回答内容")
    private String content;

}
