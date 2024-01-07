package com.btchina.content.tag.controller;


import com.btchina.core.api.CommonResult;
import com.btchina.core.api.PageResult;
import com.btchina.content.tag.model.Tag;
import com.btchina.content.tag.feign.qo.AutoCompleteQO;
import com.btchina.content.tag.feign.qo.TagAddQO;
import com.btchina.content.tag.feign.qo.TagQueryQO;
import com.btchina.content.tag.service.TagService;
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
    public CommonResult<Void> addTag(@Validated @RequestBody TagAddQO tagAddQO) {
        Boolean isSuccess = tagService.addTag(tagAddQO);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


    @ApiOperation(value ="获取标签列表")
    @PostMapping("/list")
    public CommonResult<PageResult<Tag>> list(@Validated @RequestBody TagQueryQO tagQueryQO) {
        PageResult<Tag> tags  = tagService.queryTags(tagQueryQO);
        return CommonResult.success(tags);
    }

    @ApiOperation("自动补全标签")
    @PostMapping("/autoComplete")
    public CommonResult<String> autoComplete(@Validated @RequestBody AutoCompleteQO autoCompleteQO) {
       String result = tagService.autoComplete(autoCompleteQO.getKeyword());
        return CommonResult.success(result);
    }

}

