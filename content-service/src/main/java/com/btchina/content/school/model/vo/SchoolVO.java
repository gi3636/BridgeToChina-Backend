package com.btchina.content.school.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "学校列表返回对象")
public class SchoolVO implements Serializable {

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("学校名称")
    private String name;

    @ApiModelProperty("学校简介")
    private String introduction;

    @ApiModelProperty("学校介绍")
    private String detail;

    @ApiModelProperty("学校图标")
    private String logo;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    private Date updatedTime;


}
