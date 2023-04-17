package com.btchina.user.service;

import com.btchina.user.entity.UserAction;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.model.form.user.UserActionForm;
import com.btchina.user.model.form.GetUserActionForm;
import com.btchina.user.model.vo.UserActionVO;

import java.util.List;

/**
 * <p>
 * 用户动态表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
public interface UserActionService extends IService<UserAction> {

    Boolean add(UserActionForm userActionForm);

    Boolean delete(UserActionForm userActionForm);

    List<UserActionVO> list(GetUserActionForm getUserActionForm);
}
