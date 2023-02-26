package com.btchina.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 回答表
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
@Getter
@Setter
@ApiModel(value = "Answer对象", description = "回答表")
public class Answer implements Serializable {

    @ApiModelProperty("回答id")
    private Long id;

    @ApiModelProperty("问题id")
    private Long questionId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("回答内容")
    private String content;

    @ApiModelProperty("是否最佳回答 1是 0不是")
    private Integer isBest;

    @ApiModelProperty("采用数")
    private Integer useCount;

    @ApiModelProperty("评论数")
    private Integer commentCount;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    private Date updatedTime;

    @ApiModelProperty("是否删除;1是删除，0是不删除")
    private Boolean deleted;

}
