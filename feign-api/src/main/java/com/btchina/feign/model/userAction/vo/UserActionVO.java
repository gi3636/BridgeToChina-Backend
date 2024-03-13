package com.btchina.feign.model.userAction.vo;


import com.btchina.feign.model.question.vo.QuestionVO;
import com.btchina.feign.model.user.vo.UserVO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserActionVO {

    @Schema(description ="id")
    private Long id;

    @Schema(description ="用户id")
    private Long userId;

    @Schema(description ="对象id")
    private Long objectId;

    @Schema(description ="操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注")
    private Integer actionType;

    @Schema(description ="操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注")
    private String actionName;

    @Schema(description ="对象类型 1 问题 2 用户")
    private Integer objectType;

    @Schema(description ="用户信息")
    private UserVO user;

    @Schema(description ="问题信息")
    private QuestionVO question;

    @Schema(description ="创建时间")
    private Date createdTime;

    @Schema(description ="更新时间")
    private Date updatedTime;


}
