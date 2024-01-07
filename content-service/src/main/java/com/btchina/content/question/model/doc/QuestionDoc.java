package com.btchina.content.question.model.doc;

import com.btchina.content.infra.constant.QuestionConstant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = QuestionConstant.INDEX)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class QuestionDoc {

    @Field(type = FieldType.Long)
    @ApiModelProperty("问题Id")
    private Long id;

    @Field(type = FieldType.Long)
    @ApiModelProperty("用户Id")
    private Long userId;

    @Field(type = FieldType.Long)
    @ApiModelProperty("最佳回答Id")
    private Long bestAnswerId;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", copyTo = "searchContent")
    @ApiModelProperty("问题标题")
    private String title;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart", copyTo = "searchContent")
    @ApiModelProperty("问题内容")
    private String content;

    @Field(type = FieldType.Integer)
    @ApiModelProperty("问题收藏数")
    private Integer favoriteCount;

    @Field(type = FieldType.Integer)
    @ApiModelProperty("问题点赞数")
    private Integer likeCount;

    @Field(type = FieldType.Integer)
    @ApiModelProperty("浏览数")
    private Integer viewCount;

    @Field(type = FieldType.Integer)
    @ApiModelProperty("回答数")
    private Integer answerCount;

    @Field(type = FieldType.Text)
    @ApiModelProperty("问题图片,多个图片用逗号分隔")
    private String images;

    @Field(type = FieldType.Text, copyTo = "searchContent")
    @ApiModelProperty("问题标签,多个用逗号分隔")
    private String tags;

    @Field(type = FieldType.Boolean)
    @ApiModelProperty("是否公开")
    private Boolean isPublic;

    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd'T'HH:mm:ss'+08:00' || strict_date_optional_time || epoch_millis")
    @ApiModelProperty("创建时间;创建时间")
    private Date createdTime;

    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd'T'HH:mm:ss'+08:00' || strict_date_optional_time || epoch_millis")
    @ApiModelProperty("更新时间;更新时间")
    private Date updatedTime;

    /**
     * 由其他属性copy而来，主要用于搜索功能，并非该实体类中的成员
     */
    @JsonIgnore
    @Field(type = FieldType.Text, analyzer = "ik_max_word", ignoreFields = "searchContent", excludeFromSource = true)
    String searchContent;

}
