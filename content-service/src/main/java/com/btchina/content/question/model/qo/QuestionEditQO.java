package com.btchina.content.question.model.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class QuestionEditQO {
    @ApiModelProperty("Id")
    private Long id;

    @NotBlank(message = "标题不能为空")
    @ApiModelProperty(value = "标题",example = "这是一个标题",required = true)
    private String title;

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty(value = "内容",example = "内容",required = true)
    private String content;

    @ApiModelProperty("图片")
    private List<String> images;

    @ApiModelProperty("标签")
    private List<String> tags;

    @ApiModelProperty("是否公开")
    private Boolean isPublic;

}
