package com.btchina.content.school.model.qo;

import com.btchina.core.api.valid.ValidGroup;
import com.btchina.core.api.valid.group.BizCreate;
import com.btchina.core.api.valid.group.BizQuery;
import com.btchina.core.api.valid.group.BizUpdate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
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
@ApiModel(value = "学校添加对象")
public class SchoolQO implements Serializable {

    @NotBlank(groups = BizUpdate.class, message = "school.id.notBlank")
    @Null(groups = {BizCreate.class}, message = "school.id.null")
    private String id;

    @NotBlank(groups = {BizCreate.class, BizUpdate.class, BizQuery.class}, message = "school.name.notBlank")
    @ApiModelProperty("学校名称")
    private String name;

    @Null(groups = {BizQuery.class}, message = "school.introduction.null")
    @NotBlank(groups = BizCreate.class, message = "school.introduction.notBlank")
    @ApiModelProperty("学校简介")
    private String introduction;


    @NotBlank(groups = BizCreate.class, message = "school.detail.notBlank")
    @ApiModelProperty("学校介绍")
    private String detail;


    @Null(groups = {BizQuery.class}, message = "school.logo.null")
    @NotBlank(groups = BizCreate.class, message = "school.logo.notBlank")
    @ApiModelProperty("学校图标")
    private String logo;

    @ApiModelProperty("排序")
    private Integer sort;
}
