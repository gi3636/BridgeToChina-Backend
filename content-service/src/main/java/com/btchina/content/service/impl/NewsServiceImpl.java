package com.btchina.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.content.entity.News;
import com.btchina.content.mapper.NewsMapper;
import com.btchina.content.model.form.AddNewsForm;
import com.btchina.content.model.form.QueryNewsForm;
import com.btchina.content.service.NewsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.core.api.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 资讯表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-04-23
 */
@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    @Override
    public Boolean add(AddNewsForm addNewsForm) {
        News news = new News();
        BeanUtils.copyProperties(addNewsForm, news);
        news.setLikeCount(0);
        news.setViewCount(0);
        news.setStatus(0);
        return baseMapper.insert(news) > 0;
    }

    @Override
    public PageResult<News> query(QueryNewsForm queryNewsForm) {
        Integer type = queryNewsForm.getType();
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(News::getStatus, 1);
        switch (type) {
            case 1:
                queryWrapper.orderByDesc(News::getLikeCount);
                queryWrapper.orderByDesc(News::getViewCount);
                break;
            case 2:
                queryWrapper.orderByDesc(News::getCreatedTime);
                break;
            case 3:
                queryWrapper.orderByDesc(News::getLikeCount);
                break;
        }
        Page<News> page = new Page<>(queryNewsForm.getCurrentPage(), queryNewsForm.getPageSize());
        Page<News> newsPage = baseMapper.selectPage(page, queryWrapper);
        PageResult<News> pageResult = new PageResult<>();
        pageResult.setPageSize(queryNewsForm.getPageSize());
        pageResult.setCurrentPage(queryNewsForm.getCurrentPage());
        pageResult.setList(newsPage.getRecords());
        pageResult.setTotal(newsPage.getTotal());
        pageResult.setTotalPage((int) newsPage.getPages());
        return pageResult;
    }
}
