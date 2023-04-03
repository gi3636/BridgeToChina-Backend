package com.btchina.user.model.form;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @description:注册表单
 * @author: fenggi123
 * @create: 8/13/2021 11:58 AM
 */
@Data
public class UserActionAddForm {
    @ApiModelProperty("对象id")
    private Long objectId;

    @ApiModelProperty("操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注")
    private Integer actionType;

    @ApiModelProperty("对象类型 1 问题 2 用户")
    private Integer objectType;
}
