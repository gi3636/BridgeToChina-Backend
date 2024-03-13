package com.btchina.admin.model.form;

import com.btchina.core.api.PageQueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NewsQueryForm extends PageQueryParam {

    @Schema(description ="分类ID")
    private Long categoryId;

    @Schema(description = "标题",required = true)
    private String title;

    @Schema(description = "内容",required = true)
    private String content;

    @Schema(description = "是否置顶 1是置顶 0是不置顶",required = true)
    private Boolean isTop;

    @Schema(description = "来源",required = true)
    private String comeFrom;

    @Schema(description ="审核状态 0是未审核 1是审核通过 2是审核不通过")
    private Integer status;

}

