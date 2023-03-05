package com.btchina.tag.model.form;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
@ApiModel(description = "问题查询参数对象")
public class QueryTagForm extends PageQueryParam {

    @NotNull(message = "查询类型不能为空")
    @ApiModelProperty(value = "查询类型 1:推荐 2:搜索",required = true)
    private Integer type;

    @ApiModelProperty("keyword")
    private String keyword;
}
