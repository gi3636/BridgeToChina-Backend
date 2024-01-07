package com.btchina.content.news.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.content.news.model.News;
import com.btchina.content.news.mapper.NewsMapper;
import com.btchina.content.news.model.qo.NewsAddQO;
import com.btchina.content.news.model.qo.NewsQueryQO;
import com.btchina.content.news.service.NewsService;
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
    public Boolean add(NewsAddQO newsAddQO) {
        News news = new News();
        BeanUtils.copyProperties(newsAddQO, news);
        news.setLikeCount(0);
        news.setViewCount(0);
        news.setStatus(0);
        return baseMapper.insert(news) > 0;
    }

    @Override
    public PageResult<News> query(NewsQueryQO newsQueryQO) {
        Integer type = newsQueryQO.getType();
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
                queryWrapper.eq(News::getIsTop,1);
                break;
        }
        Page<News> page = new Page<>(newsQueryQO.getCurrentPage(), newsQueryQO.getPageSize());
        Page<News> newsPage = baseMapper.selectPage(page, queryWrapper);
        PageResult<News> pageResult = new PageResult<>();
        pageResult.setPageSize(newsQueryQO.getPageSize());
        pageResult.setCurrentPage(newsQueryQO.getCurrentPage());
        pageResult.setList(newsPage.getRecords());
        pageResult.setTotal(newsPage.getTotal());
        pageResult.setTotalPage((int) newsPage.getPages());
        return pageResult;
    }

    @Override
    public News getNewsDetail(Long id) {
        return baseMapper.selectById(id);
    }
}
