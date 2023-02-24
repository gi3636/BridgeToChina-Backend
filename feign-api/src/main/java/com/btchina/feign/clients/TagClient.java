package com.btchina.feign.clients;

import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.feign.model.form.tag.AddTagForm;
import com.btchina.feign.model.form.tag.QueryQuestionTagForm;
import com.btchina.feign.model.vo.tag.TagVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name="tag-service")
public interface TagClient {

    @PostMapping("/tag/tagQuestion/add/")
    CommonResult<Void> addTag(AddTagForm addTagForm);

    @PostMapping("/tag/tagQuestion/delete/")
    CommonResult<Void> deleteTag(DeleteForm deleteForm);

    @PostMapping("/tag/tagQuestion/query/")
    List<TagVO> getTags(QueryQuestionTagForm queryQuestionTagForm);
}
