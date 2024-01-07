package com.btchina.content.question.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.btchina.content.question.model.Question;
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
