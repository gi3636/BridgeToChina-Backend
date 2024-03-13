package com.btchina.admin.controller;

import com.btchina.admin.model.form.NewsAddForm;
import com.btchina.admin.model.form.NewsQueryForm;
import com.btchina.admin.model.vo.NewsVO;
import com.btchina.admin.service.NewsAdminService;
import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "咨询后台模块")
@RestController
@RequestMapping("/admin/news")
public class NewsAdminController {

    @Autowired
    private NewsAdminService newsAdminService;


   @Parameter(name = "发布资讯")
    @PostMapping("/add")
    public CommonResult<Void> add(@Validated @RequestBody NewsAddForm newsAddForm) {
        Boolean isSuccess = newsAdminService.add(newsAddForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

   @Parameter(name = "编辑资讯")
    @PostMapping("/edit")
    public CommonResult<Void> edit(@Validated @RequestBody NewsAddForm newsAddForm) {
        Boolean isSuccess = newsAdminService.edit(newsAddForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

   @Parameter(name = "资讯列表")
    @PostMapping("/list")
    public CommonResult<PageResult<NewsVO>> query(@Validated @RequestBody NewsQueryForm newsQueryForm) {
       PageResult<NewsVO> result = newsAdminService.query(newsQueryForm);
        return CommonResult.success(result);
    }


   @Parameter(name = "删除资讯")
    @PostMapping("/del")
    public CommonResult<Void> del(@Validated @RequestBody DeleteForm deleteForm) {
        Boolean isSuccess = newsAdminService.del(deleteForm);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }
}
