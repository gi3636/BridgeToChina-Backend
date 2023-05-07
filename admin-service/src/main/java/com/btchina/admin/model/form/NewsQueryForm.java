package com.btchina.admin.model.form;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NewsQueryForm extends PageQueryParam {

    @ApiModelProperty("分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "标题",required = true)
    private String title;

    @ApiModelProperty(value = "内容",required = true)
    private String content;

    @ApiModelProperty(value = "是否置顶 1是置顶 0是不置顶",required = true)
    private Boolean isTop;

    @ApiModelProperty(value = "来源",required = true)
    private String comeFrom;

    @ApiModelProperty("审核状态 0是未审核 1是审核通过 2是审核不通过")
    private Integer status;

}

