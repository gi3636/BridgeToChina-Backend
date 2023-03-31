package com.btchina.content.service;

import com.btchina.core.api.PageResult;
import com.btchina.content.entity.Tag;
import com.btchina.content.model.doc.TagDoc;
import com.btchina.content.model.form.AddTagForm;
import com.btchina.content.model.form.QueryTagForm;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-02-06
 */
public interface TagService extends IService<Tag> {

    Boolean addTag(AddTagForm addTagForm);

    Tag selectOne(String name);

    void addEsDoc(TagDoc tagDoc);

    void updateEsDoc(TagDoc tagDoc);

    PageResult<Tag> queryTags(QueryTagForm queryTagForm);

    String autoComplete(String keyword);
}
