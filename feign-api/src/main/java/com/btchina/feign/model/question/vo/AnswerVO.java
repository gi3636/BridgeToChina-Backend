package com.btchina.feign.model.question.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@ToString
public class AnswerVO implements Serializable {


    @Schema(description ="回答id")
    private Long id;

    @Schema(description ="问题id")
    private Long questionId;

    @Schema(description ="用户id")
    private Long userId;

    @Schema(description ="用户昵称")
    private String nickname;

    @Schema(description ="采用状态")
    private Integer useStatus;

    @Schema(description ="用户头像")
    private String avatar;

    @Schema(description ="回答内容")
    private String content;

    @Schema(description ="是否最佳回答 1是 0不是")
    private Integer isBest;

    @Schema(description ="采用数")
    private Integer useCount;

    @Schema(description ="评论数")
    private Integer commentCount;

    @Schema(description ="创建时间")
    private Date createdTime;

    @Schema(description ="更新时间")
    private Date updatedTime;
}
