package com.btchina.question.model.form;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "问题查询参数对象")
public class QuestionQueryForm extends PageQueryParam {

    @ApiModelProperty("查询类型 1:热门 2:最新 3:推荐 4:我的 5:关注")
    private Integer type;
}
