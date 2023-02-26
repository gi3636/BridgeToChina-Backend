package com.btchina.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 问答表
 * </p>
 *
 * @author franky
 * @since 2023-02-01
 */

@Data
@ToString
@ApiModel(value = "Question对象", description = "问答表")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("收藏数")
    private Integer favoriteCount;

    @ApiModelProperty("点赞数")
    private Integer likeCount;

    @ApiModelProperty("浏览数")
    private Integer viewCount;

    @ApiModelProperty("回答数")
    private Integer answerCount;

    @ApiModelProperty("图片,多个图片用逗号分隔")
    private String images;

    @ApiModelProperty("是否公开 1是公开，0是个人可见")
    private Boolean isPublic;

    @ApiModelProperty("审核状态 0是未审核 1是审核通过 2是审核不通过")
    private Integer status;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    private Date updatedTime;

    @ApiModelProperty("是否删除;1是删除，0是不删除")
    private Boolean deleted;
}
