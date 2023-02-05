package com.btchina.question.service.impl;

import co.elastic.clients.elasticsearch._types.Refresh;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.question.constant.QuestionConstant;
import com.btchina.question.entity.Question;
import com.btchina.question.mapper.QuestionMapper;
import com.btchina.question.mapper.es.QuestionRepository;
import com.btchina.question.model.doc.QuestionDoc;
import com.btchina.question.model.form.AddQuestionForm;
import com.btchina.question.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p>
 * 问答表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-02-01
 */
@Service
@Slf4j
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private QuestionRepository questionRepository;

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


    /**
     * 增加到es文档
     *
     * @param questionDoc
     * @return
     */
    @Override
    public Boolean addEsDoc(QuestionDoc questionDoc) {
        try {
            QuestionDoc question = questionRepository.save(questionDoc);
            log.info("增加es文档成功: {} ", question);
            return true;
        } catch (Exception e) {
            log.error("增加es文档失败: {} ", e.getMessage(), e);
            return false;
        }
    }
}
