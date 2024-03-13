package com.btchina.feign.model.tag.vo;



import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;


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
@Tag(name = "Tag对象", description = "标签表")
public class TagVO implements Serializable {
    @Schema(description ="标签ID")
    private Long id;

    private String name;

    @Schema(description ="标签引用次数")
    private Integer count;
}
