package com.btchina.content.tag.model.doc;

import com.btchina.content.infra.constant.TagConstant;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

@Document(indexName = TagConstant.INDEX)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TagDoc {

    @Field(type = FieldType.Long)
    @Schema(description ="Id")
    private Long id;

    @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
    @Schema(description ="标签内容")
    private String name;

    @Field(type = FieldType.Integer)
    @Schema(description ="标签引用次数")
    private Integer count;

    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd'T'HH:mm:ss'+08:00' || strict_date_optional_time || epoch_millis")
    @Schema(description ="创建时间;创建时间")
    private Date createdTime;


    @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss || yyyy-MM-dd'T'HH:mm:ss'+08:00' || strict_date_optional_time || epoch_millis")
    @Schema(description ="更新时间;更新时间")
    private Date updatedTime;

}
