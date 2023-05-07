package com.btchina.admin.controller;

import com.btchina.admin.model.form.NewsAddForm;
import com.btchina.admin.model.form.NewsQueryForm;
import com.btchina.admin.model.vo.NewsVO;
import com.btchina.admin.service.NewsAdminService;
import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "咨询后台模块")
@RestController
@RequestMapping("/admin/news")
public class NewsAdminController {

    @Autowired
    private NewsAdminService newsAdminService;


    @ApiOperation(value = "发布资讯")
    @PostMapping("/add")
    public CommonResult<Void> add(@Validated @RequestBody NewsAddForm newsAddForm) {
        Boolean isSuccess = newsAdminService.add(newsAddForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "编辑资讯")
    @PostMapping("/edit")
    public CommonResult<Void> edit(@Validated @RequestBody NewsAddForm newsAddForm) {
        Boolean isSuccess = newsAdminService.edit(newsAddForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

    @ApiOperation(value = "资讯列表")
    @PostMapping("/list")
    public CommonResult<PageResult<NewsVO>> query(@Validated @RequestBody NewsQueryForm newsQueryForm) {
       PageResult<NewsVO> result = newsAdminService.query(newsQueryForm);
        return CommonResult.success(result);
    }


    @ApiOperation(value = "删除资讯")
    @PostMapping("/del")
    public CommonResult<Void> del(@Validated @RequestBody DeleteForm deleteForm) {
        Boolean isSuccess = newsAdminService.del(deleteForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }
}
