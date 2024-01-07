package com.btchina.content.action.feign.qo;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class GetUserActionForm extends PageQueryParam {

    @ApiModelProperty(value = "用户id",required = true)
    @NotNull(message = "用户id不能为空")
    private Long userId;
}
