package com.btchina.feign.clients;

import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.feign.model.form.tag.AddTagForm;
import com.btchina.feign.model.form.tag.EditQuestionTagForm;
import com.btchina.feign.model.form.tag.QueryQuestionTagForm;
import com.btchina.feign.model.vo.tag.TagVO;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name="tag-service")
public interface TagClient {

    @PostMapping("/tag/tagQuestion/add/")
    CommonResult<Void> addTag(AddTagForm addTagForm);

    @PostMapping("/tag/tagQuestion/delete/")
    CommonResult<Void> deleteTag(DeleteForm deleteForm);

    @PostMapping("/tag/tagQuestion/query/")
    List<TagVO> getTags(QueryQuestionTagForm queryQuestionTagForm);


    @PostMapping("/tag/tagQuestion/edit")
    CommonResult<Void> editQuestionTags(EditQuestionTagForm editQuestionTagForm);
}
