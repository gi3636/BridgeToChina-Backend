package com.btchina.question.model.form;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AddQuestionForm {

    @NotBlank(message = "标题不能为空")
    @ApiModelProperty(value = "标题",example = "这是一个标题",required = true)
    private String title;

    @NotBlank(message = "标题不能为空")
    @ApiModelProperty(value = "内容",example = "内容",required = true)
    private String content;

    @ApiModelProperty("图片,多个图片用逗号分隔")
    private String images;

    @NotBlank(message = "标签不能为空")
    @ApiModelProperty("标签,多个标签用逗号分隔")
    private String tags;

}
