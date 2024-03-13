package com.btchina.content.question.model.qo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
public class QuestionAddQO {

    @NotBlank(message = "标题不能为空")
    @Schema(description = "标题",example = "这是一个标题",required = true)
    private String title;

    @NotBlank(message = "内容不能为空")
    @Schema(description = "内容",example = "内容",required = true)
    private String content;

    @Schema(description ="图片")
    private List<String> images;

    @Schema(description ="标签")
    private List<String> tags;

}
