package com.btchina.feign.model.tag.vo;



import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tag对象", description = "标签表")
public class TagListVO implements Serializable {
    @Schema(description ="标签ID")
    private Long id;

    @Schema(description ="标签列表")
    private List<TagVO> tags;
}
