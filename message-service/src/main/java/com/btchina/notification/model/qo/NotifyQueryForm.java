package com.btchina.notification.model.qo;

import com.btchina.core.api.PageQueryParam;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotifyQueryForm extends PageQueryParam {

    @Schema(description ="是否已读  1已读 0未读 ")
    private Integer isRead;

    @Schema(description = "渠道类型 1 站内信 2短信 3邮箱",required = true)
    private Integer channelType;

}
