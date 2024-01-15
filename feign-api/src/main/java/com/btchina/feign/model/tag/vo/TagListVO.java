package com.btchina.feign.model.tag.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

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
public class TagListVO implements Serializable {
    @ApiModelProperty("标签ID")
    private Long id;

    @ApiModelProperty("标签列表")
    private List<TagVO> tags;
}