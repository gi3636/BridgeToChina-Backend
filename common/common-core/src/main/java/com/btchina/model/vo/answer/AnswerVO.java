package com.btchina.model.vo.answer;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AnswerVO {

    @ApiModelProperty("回答id")
    private Long id;

    @ApiModelProperty("问题id")
    private Long questionId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("回答内容")
    private String content;

    @ApiModelProperty("是否最佳回答 1是 0不是")
    private Integer isBest;

    @ApiModelProperty("是否采用 1是 0不是")
    private Integer useStatus;

    @ApiModelProperty("采用数")
    private Integer useCount;

    @ApiModelProperty("评论数")
    private Integer commentCount;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    private Date updatedTime;
}
