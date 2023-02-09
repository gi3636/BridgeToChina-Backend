package com.btchina.question.model.form;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "问题点赞参数对象")
public class QuestionLikeForm  {

    @NotNull(message = "问题id不能为空")
    @ApiModelProperty("问题id")
    private Long questionId;
}
