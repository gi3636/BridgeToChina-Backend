package com.btchina.message.mapper;

import com.btchina.message.entity.Notify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 消息通知表 Mapper 接口
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
public interface NotifyMapper extends BaseMapper<Notify> {

    void batchUpdate(List<Notify> notifies, @Param("isRead")Boolean isRead,@Param("readTime") Date readTime);
}
