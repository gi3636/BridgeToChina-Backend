package com.btchina.feign.model.question.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionVO {

    @Schema(description = "Id")
    private Long id;

    @Schema(description ="用户Id")
    private Long userId;

    @Schema(description ="用户昵称")
    private String nickname;

    @Schema(description ="用户头像")
    private String avatar;

    @Schema(description ="标题")
    private String title;

    @Schema(description ="内容")
    private String content;

    @Schema(description ="最佳回答")
    private AnswerVO bestAnswer;

    @Schema(description ="收藏数")
    private Integer favoriteCount;

    @Schema(description ="点赞数")
    private Integer likeCount;

    @Schema(description ="浏览数")
    private Integer viewCount;

    @Schema(description ="回答数")
    private Integer answerCount;

    @Schema(description ="是否点赞 0否 1是")
    private Integer likeStatus;

    @Schema(description ="是否收藏 0否 1是")
    private Integer favoriteStatus;

    @Schema(description ="图片,多个图片用逗号分隔")
    private List<String> images;

    @Schema(description ="问题标签,多个用逗号分隔")
    private List<String> tags;

    @Schema(description ="创建时间")
    private Date createdTime;

    @Schema(description ="更新时间")
    private Date updatedTime;

}
