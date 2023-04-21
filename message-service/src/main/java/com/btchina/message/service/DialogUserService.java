package com.btchina.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.core.api.PageQueryParam;
import com.btchina.message.entity.DialogUser;
import com.baomidou.mybatisplus.extension.service.IService;

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

    Page<DialogUser> getList(Long userId, PageQueryParam pageQueryParam);

    Boolean addUnreadCount(Long dialogId, Long receiverId);

    Boolean reduceUnreadCount(Long dialogId, Long userId);
}
