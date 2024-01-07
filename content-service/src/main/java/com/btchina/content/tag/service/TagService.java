package com.btchina.content.tag.service;

import com.btchina.core.api.PageResult;
import com.btchina.content.tag.model.Tag;
import com.btchina.content.tag.model.doc.TagDoc;
import com.btchina.content.tag.feign.qo.TagAddQO;
import com.btchina.content.tag.feign.qo.TagQueryQO;
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

    Boolean addTag(TagAddQO tagAddQO);

    Tag selectOne(String name);

    void addEsDoc(TagDoc tagDoc);

    void updateEsDoc(TagDoc tagDoc);

    PageResult<Tag> queryTags(TagQueryQO tagQueryQO);

    String autoComplete(String keyword);
}
