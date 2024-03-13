package com.btchina.admin.model.form;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@ToString
public class NewsEditForm {


    @NotNull(message = "分类ID不能为空")
    @Schema(description ="分类ID")
    private Long categoryId;

    @NotBlank(message = "标题不能为空")
    @Schema(description = "标题",required = true)
    private String title;

    @NotBlank(message = "内容不能为空")
    @Schema(description = "内容",required = true)
    private String content;

    @Min(value = 0,message = "是否置顶只能是0或1")
    @NotNull(message = "页码不能为空")
    @Schema(description = "是否置顶 1是置顶 0是不置顶",required = true)
    private Boolean isTop;

    @NotBlank(message = "来源不能为空")
    @Schema(description = "来源",required = true)
    private String comeFrom;

    @Schema(description ="点赞数")
    private Integer likeCount;

}
