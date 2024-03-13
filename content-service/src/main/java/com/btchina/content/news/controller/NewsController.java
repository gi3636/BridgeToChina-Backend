package com.btchina.content.news.controller;


import com.btchina.content.news.model.News;
import com.btchina.content.news.model.qo.NewsAddQO;
import com.btchina.content.news.model.qo.NewsQueryQO;
import com.btchina.content.news.service.NewsService;
import com.btchina.core.api.CommonResult;

import com.btchina.core.api.PageResult;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 资讯表 前端控制器
 * </p>
 *
 * @author franky
 * @since 2023-04-23
 */
@RestController
@Tag(name = "资讯")
@RequestMapping("/news")
public class NewsController {


    @Autowired
    private NewsService newsService;

    @Parameter(name = "发布资讯")
    @PostMapping("/add")
    public CommonResult<Void> add(@Validated @RequestBody NewsAddQO newsAddQO) {
        Boolean isSuccess = newsService.add(newsAddQO);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


    @Parameter(name = "获取资讯列表")
    @PostMapping("/list")
    public CommonResult<PageResult<News>> list(@Validated @RequestBody NewsQueryQO newsQueryQO) {
        PageResult<News> result = newsService.query(newsQueryQO);
        return CommonResult.success(result);
    }

    @Parameter(name = "获取资讯详情")
    @GetMapping("/detail/{id}")
    public CommonResult<News> detail(@PathVariable("id") Long id) {
        News result = newsService.getNewsDetail(id);
        return CommonResult.success(result);
    }


}

