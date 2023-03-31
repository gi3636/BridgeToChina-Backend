package com.btchina.content.model.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "问题收藏表单")
public class QuestionFavouriteForm {

    @NotNull(message = "问题id不能为空")
    @ApiModelProperty(required = true, value = "问题id")
    private Long questionId;

    @NotNull(message = "收藏状态不能为空")
    @ApiModelProperty(required = true, value = "收藏状态 1:收藏 2:取消收藏", example = "1")
    private Integer status;
}
