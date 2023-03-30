package com.btchina.user.service.impl;

import com.btchina.user.entity.UserAction;
import com.btchina.user.mapper.UserActionMapper;
import com.btchina.user.service.UserActionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.user.entity.UserAction;
import com.btchina.user.mapper.UserActionMapper;
import com.btchina.user.service.UserActionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户动态表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
@Service
public class UserActionServiceImpl extends ServiceImpl<UserActionMapper, UserAction> implements UserActionService {

}
