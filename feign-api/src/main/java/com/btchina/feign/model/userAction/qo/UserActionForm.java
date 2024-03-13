package com.btchina.feign.model.userAction.qo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import jakarta.validation.constraints.NotNull;

@Data
@ToString
public class UserActionForm {

    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户id", required = true)
    private Long userId;
    @NotNull(message = "对象id不能为空")
    @Schema(description = "对象id", required = true)
    private Long objectId;
    @NotNull(message = "操作类型不能为空")
    @Schema(description = "操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注", required = true)
    private Integer actionType;
    @NotNull(message = "对象类型不能为空")
    @Schema(description = "对象类型 1 问题 2 用户 3 评论", required = true)
    private Integer objectType;
}
