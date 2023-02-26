package com.btchina.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 标签表
 * </p>
 *
 * @author franky
 * @since 2023-02-06
 */
@Getter
@Setter
@ApiModel(value = "Tag对象", description = "标签表")
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("标签ID")
    private Long id;

    @ApiModelProperty("标签内容")
    private String name;

    @ApiModelProperty("创建时间")
    private Date createdTime;

    @ApiModelProperty("更新时间")
    private Date updatedTime;

    @ApiModelProperty("是否删除;1是删除，0是不删除")
    private Boolean deleted;


}
