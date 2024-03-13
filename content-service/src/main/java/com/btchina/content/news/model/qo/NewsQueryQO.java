package com.btchina.content.news.model.qo;

import com.btchina.core.api.PageQueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NewsQueryQO extends PageQueryParam {
    @Schema(description = "分类ID")
    private Long categoryId;

    @Schema(description = "查询类型 1:热门 2:最新 3:置顶 ", required = true, example = "1")
    private Integer type;
}
