package com.btchina.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.btchina.question.entity.Question;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 问答表 Mapper 接口
 * </p>
 *
 * @author franky
 * @since 2023-02-01
 */
@Mapper
public interface QuestionMapper extends BaseMapper<Question> {

}
