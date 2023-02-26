package com.btchina.comment.model.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 评论表
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
@Data
@ToString
public class CommentVO implements Serializable {


    @ApiModelProperty("评论id")
    private Long id;

    @ApiModelProperty("回答id")
    private Long answerId;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("回复的评论id")
    private Long parentId;

    @ApiModelProperty("回复的对象id")
    private Long toUserId;

    @ApiModelProperty("回复的对象昵称")
    private String toNickname;

    @ApiModelProperty("评论内容")
    private String content;

    @ApiModelProperty("评论点赞数")
    private Integer likeCount;
    @ApiModelProperty("是否点赞 0-否 1-是")
    private Integer likeStatus;
    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
