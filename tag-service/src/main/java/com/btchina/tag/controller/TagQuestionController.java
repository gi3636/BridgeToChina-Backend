package com.btchina.tag.controller;


import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.tag.model.form.AddTagForm;
import com.btchina.tag.model.form.EditQuestionTagForm;
import com.btchina.tag.model.form.QueryQuestionTagForm;
import com.btchina.tag.model.vo.TagVO;
import com.btchina.tag.service.TagQuestionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 标签问题表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-02-06
 */
@Api(tags = "问题标签模块")
@RestController
@RequestMapping("/tag/tagQuestion")
public class TagQuestionController {
    @Autowired
    private TagQuestionService tagQuestionService;

    @ApiOperation(value = "添加问题标签")
    @PostMapping("/add")
    public CommonResult<Void> addTag(@Validated @RequestBody AddTagForm addTagForm) {
        Boolean isSuccess = tagQuestionService.addTag(addTagForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "删除问题标签")
    @PostMapping("/delete")
    public CommonResult<Void> deleteTag(@RequestBody DeleteForm deleteForm) {
        Boolean isSuccess = tagQuestionService.deleteTag(deleteForm.getId());
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


    @ApiOperation(value = "查询问题标签")
    @PostMapping("/query")
    public List<TagVO> getTags(@Validated @RequestBody QueryQuestionTagForm queryQuestionTagForm) {
        return tagQuestionService.queryTag(queryQuestionTagForm);
    }


    @ApiOperation(value = "更改问题标签")
    @PostMapping("/edit")
    public CommonResult<Void> editQuestionTags(@Validated @RequestBody EditQuestionTagForm editQuestionTagForm) {
        Boolean isSuccess = tagQuestionService.editTag(editQuestionTagForm);
        if (!isSuccess) {
            return null;
        }
        return CommonResult.success(null);
    }

}

