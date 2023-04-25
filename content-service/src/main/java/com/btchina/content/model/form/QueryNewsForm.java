package com.btchina.content.model.form;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class QueryNewsForm extends PageQueryParam {
    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "查询类型 1:热门 2:最新 ", required = true, example = "1")
    private Integer type;
}
