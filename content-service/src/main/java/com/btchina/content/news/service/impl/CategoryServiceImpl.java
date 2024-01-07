package com.btchina.content.news.service.impl;

import com.btchina.content.news.model.Category;
import com.btchina.content.news.mapper.CategoryMapper;
import com.btchina.content.news.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 类别表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-04-23
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
