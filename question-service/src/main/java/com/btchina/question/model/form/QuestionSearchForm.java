package com.btchina.question.model.form;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel(description = "问题搜索")
public class QuestionSearchForm extends PageQueryParam {
    @ApiModelProperty("关键字")
    public String keyword;
}
