package com.btchina.content.tag.feign;

import com.btchina.content.question.feign.qo.QueryQuestionTagQO;
import com.btchina.content.question.feign.qo.QuestionEditTagQO;
import com.btchina.content.tag.feign.qo.TagAddQO;
import com.btchina.content.tag.feign.vo.TagVO;
import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
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
