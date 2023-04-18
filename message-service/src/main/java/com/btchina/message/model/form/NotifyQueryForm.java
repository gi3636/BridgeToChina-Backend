package com.btchina.message.model.form;

import com.btchina.core.api.PageQueryParam;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotifyQueryForm extends PageQueryParam {

    @ApiModelProperty("是否已读  1已读 0未读 ")
    private Integer isRead;

    @ApiModelProperty(value = "渠道类型 1 站内信 2短信 3邮箱",required = true)
    private Integer channelType;

}
