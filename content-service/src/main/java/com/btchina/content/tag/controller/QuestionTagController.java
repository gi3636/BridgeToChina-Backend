package com.btchina.content.tag.controller;


import com.btchina.content.tag.service.TagQuestionService;
import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.feign.model.tag.qo.QueryQuestionTagQO;
import com.btchina.feign.model.tag.qo.QuestionEditTagQO;
import com.btchina.feign.model.tag.qo.TagAddQO;
import com.btchina.feign.model.tag.vo.TagListVO;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "问题标签模块")
@RestController
@RequestMapping("/tag/tagQuestion")
public class QuestionTagController {
    @Autowired
    private TagQuestionService tagQuestionService;

   @Parameter(name = "添加问题标签")
    @PostMapping("/add")
    public CommonResult<Void> addTag(@Validated @RequestBody TagAddQO tagAddQO) {
        Boolean isSuccess = tagQuestionService.addTag(tagAddQO);
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }

   @Parameter(name = "删除问题标签")
    @PostMapping("/delete")
    public CommonResult<Void> deleteTag(@RequestBody DeleteForm deleteForm) {
        Boolean isSuccess = tagQuestionService.deleteTag(deleteForm.getId());
        if (!isSuccess) {
            return CommonResult.failed();
        }
        return CommonResult.success(null);
    }


   @Parameter(name = "查询问题标签")
    @PostMapping("/query")
    public List<TagListVO> getTags(@Validated @RequestBody QueryQuestionTagQO queryQuestionTagQO) {
        return tagQuestionService.queryTag(queryQuestionTagQO);
    }


   @Parameter(name = "更改问题标签")
    @PostMapping("/edit")
    public CommonResult<Void> editQuestionTags(@Validated @RequestBody QuestionEditTagQO editQuestionTagForm) {
        Boolean isSuccess = tagQuestionService.editTag(editQuestionTagForm);
        if (!isSuccess) {
            return null;
        }
        return CommonResult.success(null);
    }

}

