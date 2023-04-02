package com.btchina.message.service;

import com.btchina.core.api.PageQueryParam;
import com.btchina.message.entity.DialogUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户对话表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
public interface DialogUserService extends IService<DialogUser> {
    Boolean add(Long userId,Long toUserId, Long dialogId);

    List<DialogUser> getList(Long userId, PageQueryParam pageQueryParam);

}
