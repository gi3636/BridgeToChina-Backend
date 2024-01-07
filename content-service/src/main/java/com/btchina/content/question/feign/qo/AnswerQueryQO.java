package com.btchina.content.question.feign.qo;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AnswerQueryQO extends PageQueryParam {
    @NotNull(message = "问题id不能为空")
    @ApiModelProperty("问题id")
    private Long questionId;

}
