package com.btchina.content.school.model.qo;



import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "学校列表查询对象")
public class SchoolListQO implements Serializable {

    @Schema(description = "学校名称")
    private String name;

}
