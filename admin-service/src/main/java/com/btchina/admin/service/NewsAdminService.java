package com.btchina.admin.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.admin.entity.News;
import com.btchina.admin.model.form.NewsAddForm;
import com.btchina.admin.model.form.NewsQueryForm;
import com.btchina.admin.model.vo.NewsVO;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;

/**
 * <p>
 * 资讯表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-05-07
 */
public interface NewsAdminService extends IService<News> {

    Boolean add(NewsAddForm newsAddForm);

    Boolean edit(NewsAddForm newsAddForm);

    PageResult<NewsVO> query(NewsQueryForm newsQueryForm);

    Boolean del(DeleteForm deleteForm);
}
