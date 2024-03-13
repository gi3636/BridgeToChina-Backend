package com.btchina.admin.model.vo;

import com.baomidou.mybatisplus.annotation.*;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 资讯表
 * </p>
 *
 * @author franky
 * @since 2023-05-07
 */
@Data
@ToString
@Schema(name = "News对象", description = "资讯表")
public class NewsVO implements Serializable {

    private Long id;
    @Schema(description ="分类ID")
    private Long categoryId;

    @Schema(description ="标题")
    private String title;

    @Schema(description ="内容")
    private String content;

    @Schema(description ="是否置顶 1是置顶 0是不置顶")
    private Boolean isTop;

    @Schema(description ="来源")
    private String comeFrom;

    @Schema(description ="点赞数")
    private Integer likeCount;

    @Schema(description ="浏览数")
    private Integer viewCount;

    @Schema(description ="审核状态 0是未审核 1是审核通过 2是审核不通过")
    private Integer status;

    @Schema(description ="创建时间")
    private Date createdTime;

    @Schema(description ="更新时间")
    private Date updatedTime;

}
