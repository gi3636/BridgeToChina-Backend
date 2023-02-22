package com.btchina.tag.controller;


import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.tag.model.form.AddTagForm;
import com.btchina.tag.service.TagQuestionService;
import com.btchina.tag.service.TagService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 标签问题表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-02-06
 */
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
}

