package com.btchina.tag.service;

import com.btchina.tag.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.tag.model.form.AddTagForm;

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
}
