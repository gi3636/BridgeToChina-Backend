package com.btchina.content.comment.model.vo;



import io.swagger.v3.oas.annotations.media.Schema;
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


    @Schema(description ="评论id")
    private Long id;

    @Schema(description ="回答id")
    private Long answerId;

    @Schema(description ="用户id")
    private Long userId;

    @Schema(description ="用户昵称")
    private String nickname;

    @Schema(description ="用户头像")
    private String avatar;

    @Schema(description ="回复的评论id")
    private Long parentId;

    @Schema(description ="回复的对象id")
    private Long toUserId;

    @Schema(description ="回复的对象昵称")
    private String toNickname;

    @Schema(description ="评论内容")
    private String content;

    @Schema(description ="评论点赞数")
    private Integer likeCount;
    @Schema(description ="是否点赞 0-否 1-是")
    private Integer likeStatus;
    @Schema(description ="创建时间")
    private Date createdTime;

    @Schema(description ="更新时间")
    private Date updatedTime;

}
