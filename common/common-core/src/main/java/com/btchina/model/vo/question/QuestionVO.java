package com.btchina.model.vo.question;

import com.btchina.model.vo.answer.AnswerVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class QuestionVO {

    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("用户Id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("用户头像")
    private String avatar;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("最佳回答")
    private AnswerVO bestAnswer;

    @ApiModelProperty("收藏数")
    private Integer favoriteCount;

    @ApiModelProperty("点赞数")
    private Integer likeCount;

    @ApiModelProperty("浏览数")
    private Integer viewCount;

    @ApiModelProperty("回答数")
    private Integer answerCount;

    @ApiModelProperty("是否点赞 0否 1是")
    private Integer likeStatus;

    @ApiModelProperty("是否收藏 0否 1是")
    private Integer favoriteStatus;

    @ApiModelProperty("图片,多个图片用逗号分隔")
    private List<String> images;

    @ApiModelProperty("问题标签,多个用逗号分隔")
    private List<String> tags;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    private Date updatedTime;

}
