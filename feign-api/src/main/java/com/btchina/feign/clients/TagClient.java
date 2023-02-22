package com.btchina.feign.clients;

import com.btchina.core.api.CommonResult;
import com.btchina.core.api.DeleteForm;
import com.btchina.feign.model.form.AddTagForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="tag-service")
public interface TagClient {

    @PostMapping("/tag/tagQuestion/add/")
    CommonResult<Void> addTag(AddTagForm addTagForm);

    @PostMapping("/tag/tagQuestion/delete/")
    CommonResult<Void> deleteTag(DeleteForm deleteForm);
}
