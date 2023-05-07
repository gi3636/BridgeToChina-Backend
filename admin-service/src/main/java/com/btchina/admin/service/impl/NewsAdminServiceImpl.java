package com.btchina.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.admin.entity.News;
import com.btchina.admin.mapper.NewsAdminMapper;
import com.btchina.admin.model.form.NewsAddForm;
import com.btchina.admin.model.form.NewsQueryForm;
import com.btchina.admin.model.vo.NewsVO;
import com.btchina.admin.service.NewsAdminService;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 资讯表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-05-07
 */
@Service
public class NewsAdminServiceImpl extends ServiceImpl<NewsAdminMapper, News> implements NewsAdminService {
    @Override
    public Boolean add(NewsAddForm newsAddForm) {
        News news = new News();
        news.setViewCount(0);
        news.setLikeCount(0);
        BeanUtils.copyProperties(newsAddForm, news);
        return baseMapper.insert(news) > 0;
    }

    @Override
    public Boolean edit(NewsAddForm newsAddForm) {
        News news = new News();
        BeanUtils.copyProperties(newsAddForm, news);
        return baseMapper.updateById(news) > 0;
    }

    @Override
    public PageResult<NewsVO> query(NewsQueryForm newsQueryForm) {
        LambdaQueryWrapper<News> queryWrapper = new LambdaQueryWrapper<>();
        if (newsQueryForm.getCategoryId() != null) {
            queryWrapper.eq(News::getCategoryId, newsQueryForm.getCategoryId());
        }
        if (newsQueryForm.getTitle() != null) {
            queryWrapper.like(News::getTitle, newsQueryForm.getTitle());
        }
        if (newsQueryForm.getContent() != null) {
            queryWrapper.like(News::getContent, newsQueryForm.getContent());
        }
        if (newsQueryForm.getIsTop() != null) {
            queryWrapper.eq(News::getIsTop, newsQueryForm.getIsTop());
        }
        if (newsQueryForm.getStatus() != null) {
            queryWrapper.eq(News::getStatus, newsQueryForm.getStatus());
        }

        Page<News> page = new Page<>(newsQueryForm.getCurrentPage(), newsQueryForm.getPageSize());
        Page<News> newsPage = baseMapper.selectPage(page, queryWrapper);
        PageResult<NewsVO> pageResult = new PageResult<>();
        List<NewsVO> newsVOList = new ArrayList<>();
        for (News news : newsPage.getRecords()) {
            NewsVO newsVO = new NewsVO();
            BeanUtils.copyProperties(news, newsVO);
            newsVOList.add(newsVO);
        }
        pageResult.setList(newsVOList);
        pageResult.setTotal(newsPage.getTotal());
        pageResult.setTotalPage((int) newsPage.getPages());
        pageResult.setCurrentPage((int) newsPage.getCurrent());
        return pageResult;
    }

    @Override
    public Boolean del(DeleteForm deleteForm) {
        return this.baseMapper.deleteById(deleteForm.getId()) > 0;
    }

}
