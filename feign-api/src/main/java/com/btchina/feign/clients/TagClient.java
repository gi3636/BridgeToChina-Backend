package com.btchina.feign.clients;


import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.feign.model.tag.qo.QueryQuestionTagQO;
import com.btchina.feign.model.tag.qo.QuestionEditTagQO;
import com.btchina.feign.model.tag.qo.TagAddQO;
import com.btchina.feign.model.tag.vo.TagVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name="tag-service")
public interface TagClient {

    @PostMapping("/tag/tagQuestion/add/")
    CommonResult<Void> addTag(TagAddQO tagAddQO);

    @PostMapping("/tag/tagQuestion/delete/")
    CommonResult<Void> deleteTag(DeleteForm deleteForm);

    @PostMapping("/tag/tagQuestion/query/")
    List<TagVO> getTags(QueryQuestionTagQO queryQuestionTagQO);


    @PostMapping("/tag/tagQuestion/edit")
    CommonResult<Void> editQuestionTags(QuestionEditTagQO editQuestionTagForm);
}
