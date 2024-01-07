package com.btchina.content.action.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.content.action.feign.qo.GetUserActionForm;
import com.btchina.content.action.feign.qo.UserActionForm;
import com.btchina.content.action.feign.vo.UserActionVO;
import com.btchina.content.action.model.UserAction;

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
