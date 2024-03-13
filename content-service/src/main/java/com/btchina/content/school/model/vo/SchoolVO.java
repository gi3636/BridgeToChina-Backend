package com.btchina.content.school.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 学校表
 * </p>
 *
 * @author franky
 * @since 2024-01-15
 */
@Data
@Tag(name = "学校列表返回对象")
public class SchoolVO implements Serializable {

    @Schema(description ="id")
    private String id;

    @Schema(description ="学校名称")
    private String name;

    @Schema(description ="学校简介")
    private String introduction;

    @Schema(description ="学校介绍")
    private String detail;

    @Schema(description ="学校图标")
    private String logo;

    @Schema(description ="排序")
    private Integer sort;

    @Schema(description ="创建时间")
    private Date createdTime;

    @Schema(description ="更新时间")
    private Date updatedTime;

}
