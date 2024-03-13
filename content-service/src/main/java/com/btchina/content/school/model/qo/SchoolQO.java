package com.btchina.content.school.model.qo;

import com.btchina.core.api.valid.ValidGroup;
import com.btchina.core.api.valid.group.BizCreate;
import com.btchina.core.api.valid.group.BizQuery;
import com.btchina.core.api.valid.group.BizUpdate;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import java.io.Serializable;

/**
 * <p>
 * 学校表
 * </p>
 *
 * @author franky
 * @since 2024-01-15
 */
@Data
@Tag(name = "学校添加对象")
public class SchoolQO implements Serializable {

    @NotBlank(groups = BizUpdate.class, message = "school.id.notBlank")
    @Null(groups = {BizCreate.class}, message = "school.id.null")
    private String id;

    @NotBlank(groups = {BizCreate.class, BizUpdate.class, BizQuery.class}, message = "school.name.notBlank")
    @Schema(description ="学校名称")
    private String name;

    @Null(groups = {BizQuery.class}, message = "school.introduction.null")
    @NotBlank(groups = BizCreate.class, message = "school.introduction.notBlank")
    @Schema(description ="学校简介")
    private String introduction;


    @NotBlank(groups = BizCreate.class, message = "school.detail.notBlank")
    @Schema(description ="学校介绍")
    private String detail;


    @Null(groups = {BizQuery.class}, message = "school.logo.null")
    @NotBlank(groups = BizCreate.class, message = "school.logo.notBlank")
    @Schema(description ="学校图标")
    private String logo;

    @Schema(description ="排序")
    private Integer sort;
}
