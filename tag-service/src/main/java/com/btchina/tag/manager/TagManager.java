package com.btchina.tag.manager;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.tag.entity.Tag;
import com.btchina.tag.mapper.TagMapper;
import com.btchina.tag.model.form.AddTagForm;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TagManager extends ServiceImpl<TagMapper, Tag> {

    /**
     * 添加标签
     * @param tagList
     * @return
     */
    public Boolean addTag(List<String> tagList) {
        // 1. 标签是否存在
        for (String name : tagList) {
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
