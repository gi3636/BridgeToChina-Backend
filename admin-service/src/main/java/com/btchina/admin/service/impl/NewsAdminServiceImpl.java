package com.btchina.admin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.admin.entity.News;
import com.btchina.admin.mapper.NewsAdminMapper;
import com.btchina.admin.service.NewsAdminService;
import org.springframework.stereotype.Service;

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

}
