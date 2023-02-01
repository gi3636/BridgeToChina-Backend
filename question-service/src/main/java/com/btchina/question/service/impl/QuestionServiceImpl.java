package com.btchina.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.question.entity.Question;
import com.btchina.question.mapper.QuestionMapper;
import com.btchina.question.service.QuestionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 问答表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-02-01
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

}
