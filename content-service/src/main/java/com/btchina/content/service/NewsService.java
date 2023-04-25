package com.btchina.content.service;

import com.btchina.content.entity.News;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.content.model.form.AddNewsForm;
import com.btchina.content.model.form.QueryNewsForm;
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

    Boolean add(AddNewsForm addNewsForm);

    PageResult<News> query(QueryNewsForm queryNewsForm);
}
