package com.btchina.message.service;

import com.btchina.core.api.PageResult;
import com.btchina.message.entity.Notify;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.message.model.form.NotifyQueryForm;
import com.btchina.message.model.vo.NotifyVO;
import com.btchina.model.form.message.NotifyAddForm;

/**
 * <p>
 * 消息通知表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
public interface NotifyService extends IService<Notify> {

    Boolean add(NotifyAddForm notifyAddForm);

    PageResult<NotifyVO> list(Long userId, NotifyQueryForm notifyQueryForm);
}
