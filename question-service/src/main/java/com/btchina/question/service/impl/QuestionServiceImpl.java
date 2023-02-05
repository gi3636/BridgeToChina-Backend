package com.btchina.question.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.question.constant.QuestionConstant;
import com.btchina.question.entity.Question;
import com.btchina.question.mapper.QuestionMapper;
import com.btchina.question.model.form.AddQuestionForm;
import com.btchina.question.service.QuestionService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Boolean addQuestion(AddQuestionForm addQuestionForm) {
        Question question = new Question();
        question.setTitle(addQuestionForm.getTitle());
        question.setContent(addQuestionForm.getContent());
        question.setUserId(1L);
        Boolean isSuccess = this.baseMapper.insert(question) > 0;
        if (isSuccess) {
            rabbitTemplate.convertAndSend(QuestionConstant.EXCHANGE_NAME, QuestionConstant.INSERT_KEY, question);
        }
        return isSuccess;
    }
}
