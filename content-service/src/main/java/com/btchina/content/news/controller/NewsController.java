package com.btchina.content.news.controller;


import com.btchina.content.news.model.News;
import com.btchina.content.news.feign.qo.NewsAddQO;
import com.btchina.content.news.feign.qo.NewsQueryQO;
import com.btchina.content.news.service.NewsService;
import com.btchina.core.api.CommonResult;
import com.btchina.core.api.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "资讯")
@RequestMapping("/news")
public class NewsController {


    @Autowired
    private NewsService newsService;

    @ApiOperation(value = "发布资讯")
    @PostMapping("/add")
    public CommonResult<Void> add(@Validated @RequestBody NewsAddQO newsAddQO) {
        Boolean isSuccess = newsService.add(newsAddQO);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


    @ApiOperation(value = "获取资讯列表")
    @PostMapping("/list")
    public CommonResult<PageResult<News>> list(@Validated @RequestBody NewsQueryQO newsQueryQO) {
        PageResult<News> result = newsService.query(newsQueryQO);
        return CommonResult.success(result);
    }

    @ApiOperation(value = "获取资讯详情")
    @GetMapping("/detail/{id}")
    public CommonResult<News> detail(@PathVariable("id") Long id) {
        News result = newsService.getNewsDetail(id);
        return CommonResult.success(result);
    }


}

