package com.btchina.message.service;

import com.btchina.core.api.PageQueryParam;
import com.btchina.core.api.PageResult;
import com.btchina.message.entity.Dialog;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.message.model.form.DialogAddForm;
import com.btchina.message.model.vo.DialogVO;

/**
 * <p>
 * 会话详情表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
public interface DialogService extends IService<Dialog> {

    Dialog add(Long userId, DialogAddForm dialogAddForm);

    PageResult<DialogVO> getList(Long userId, PageQueryParam pageQueryParam);

    Boolean updateLastMessage(String dialogId, String msgId, String content);
}
