package com.btchina.content.news.model.qo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class NewsAddQO {

    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "标题",required = true)
    private String title;

    @Schema(description = "内容",required = true)
    private String content;

    @Schema(description = "是否置顶 1是置顶 0是不置顶",required = true)
    private Boolean isTop;

    @Schema(description ="来源")
    private String comeFrom;

}
