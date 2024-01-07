package com.btchina.content.news.service;

import com.btchina.content.news.model.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.content.news.feign.qo.NewsAddQO;
import com.btchina.content.news.feign.qo.NewsQueryQO;
import com.btchina.core.api.PageResult;

/**
 * <p>
 * 资讯表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-04-23
 */
public interface NewsService extends IService<News> {

    Boolean add(NewsAddQO newsAddQO);

    PageResult<News> query(NewsQueryQO newsQueryQO);

    News getNewsDetail(Long id);
}
