package com.btchina.content.controller;


import com.btchina.core.api.CommonResult;
import com.btchina.core.api.PageResult;
import com.btchina.content.entity.Tag;
import com.btchina.content.model.form.AutoCompleteForm;
import com.btchina.content.model.form.AddTagForm;
import com.btchina.content.model.form.QueryTagForm;
import com.btchina.content.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-02-06
 */
@RestController
@Api(tags = "标签模块")
@RequestMapping("/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @ApiOperation(value = "添加标签")
    @PostMapping("/add")
    public CommonResult<Void> addTag(@Validated @RequestBody AddTagForm addTagForm) {
        Boolean isSuccess = tagService.addTag(addTagForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


    @ApiOperation(value ="获取标签列表")
    @PostMapping("/list")
    public CommonResult<PageResult<Tag>> list(@Validated @RequestBody QueryTagForm queryTagForm) {
        PageResult<Tag> tags  = tagService.queryTags(queryTagForm);
        return CommonResult.success(tags);
    }

    @ApiOperation("自动补全标签")
    @PostMapping("/autoComplete")
    public CommonResult<String> autoComplete(@Validated @RequestBody AutoCompleteForm autoCompleteForm) {
       String result = tagService.autoComplete(autoCompleteForm.getKeyword());
        return CommonResult.success(result);
    }

}

