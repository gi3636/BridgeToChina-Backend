package com.btchina.content.news.feign.qo;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NewsQueryQO extends PageQueryParam {
    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "查询类型 1:热门 2:最新 3:置顶 ", required = true, example = "1")
    private Integer type;
}
