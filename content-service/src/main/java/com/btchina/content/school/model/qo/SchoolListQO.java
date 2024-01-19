package com.btchina.content.school.model.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel(value = "学校列表查询对象")
public class SchoolListQO implements Serializable {

    @ApiModelProperty(value = "学校名称")
    private String name;

}
