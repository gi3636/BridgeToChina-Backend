package com.btchina.content.controller;


import com.btchina.content.entity.News;
import com.btchina.content.model.form.AddNewsForm;
import com.btchina.content.model.form.QueryNewsForm;
import com.btchina.content.service.NewsService;
import com.btchina.core.api.CommonResult;
import com.btchina.core.api.PageResult;
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
 * 资讯表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-04-23
 */
@RestController
@Api(tags = "资讯")
@RequestMapping("/news")
public class NewsController {


    @Autowired
    private NewsService newsService;

    @ApiOperation(value = "发布资讯")
    @PostMapping("/add")
    public CommonResult<Void> add(@Validated @RequestBody AddNewsForm addNewsForm) {
        Boolean isSuccess = newsService.add(addNewsForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


    @ApiOperation(value = "获取资讯列表")
    @PostMapping("/list")
    public CommonResult<PageResult<News>> list(@Validated @RequestBody QueryNewsForm queryNewsForm) {
        PageResult<News> result = newsService.query(queryNewsForm);
        return CommonResult.success(result);
    }


}

