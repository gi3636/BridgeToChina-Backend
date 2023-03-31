package com.btchina.content.model.form;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(description = "问题查询参数对象")
public class QueryTagForm extends PageQueryParam {


    @ApiModelProperty("keyword")
    private String keyword;
}
