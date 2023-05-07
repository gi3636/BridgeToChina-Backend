package com.btchina.admin.model.form;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@ToString
public class NewsAddForm {
    @NotNull(message = "分类ID不能为空")
    @ApiModelProperty("分类ID")
    private Long categoryId;

    @NotBlank(message = "标题不能为空")
    @ApiModelProperty(value = "标题",required = true)
    private String title;

    @NotBlank(message = "内容不能为空")
    @ApiModelProperty(value = "内容",required = true)
    private String content;

    @Min(value = 0,message = "是否置顶只能是0或1")
    @NotNull(message = "页码不能为空")
    @ApiModelProperty(value = "是否置顶 1是置顶 0是不置顶",required = true)
    private Boolean isTop;

    @NotBlank(message = "来源不能为空")
    @ApiModelProperty(value = "来源",required = true)
    private String comeFrom;

    @ApiModelProperty("点赞数")
    private Integer likeCount;
}
