package com.btchina.content.question.feign.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "回答采用参数对象")
public class AnswerUseQO {
    @NotNull(message = "回答id不能为空")
    @ApiModelProperty("回答id")
    private Long id;

    @NotNull(message = "状态不能为空")
    @ApiModelProperty("状态 0是取消 1是采用")
    private Integer status;

}
