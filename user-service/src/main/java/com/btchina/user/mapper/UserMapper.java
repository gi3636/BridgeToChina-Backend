package com.btchina.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.btchina.user.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author franky
 * @since 2023-01-30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
