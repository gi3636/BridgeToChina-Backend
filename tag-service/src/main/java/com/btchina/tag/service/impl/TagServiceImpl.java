package com.btchina.tag.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.btchina.tag.entity.Tag;
import com.btchina.tag.mapper.TagMapper;
import com.btchina.tag.model.form.AddTagForm;
import com.btchina.tag.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.lucene.search.similarities.Lambda;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-02-06
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public Boolean addTag(AddTagForm addTagForm) {
        List<String> tags = addTagForm.getTags();
        Long id = addTagForm.getId();
        // 1. 标签是否存在
        for (String name : tags) {
            LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Tag::getName, name);
            // 2. 不存在则创建
            Tag tag = getOne(queryWrapper);
            if (tag == null) {
                tag = new Tag();
                tag.setName(name);
                save(tag);
            }
        }
        return true;
    }
}
