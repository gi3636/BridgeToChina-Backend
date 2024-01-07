package com.btchina.content.news.feign.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NewsAddQO {

    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "标题",required = true)
    private String title;

    @ApiModelProperty(value = "内容",required = true)
    private String content;

    @ApiModelProperty(value = "是否置顶 1是置顶 0是不置顶",required = true)
    private Boolean isTop;

    @ApiModelProperty("来源")
    private String comeFrom;

}
