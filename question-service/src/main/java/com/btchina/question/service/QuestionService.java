package com.btchina.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.core.api.PageResult;
import com.btchina.question.entity.Question;
import com.btchina.question.model.doc.QuestionDoc;
import com.btchina.question.model.form.AddQuestionForm;
import com.btchina.question.model.form.QuestionQueryForm;
import com.btchina.question.model.vo.QuestionVO;
import org.springframework.data.elasticsearch.core.SearchHits;

/**
 * <p>
 * 问答表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-02-01
 */
public interface QuestionService extends IService<Question> {

    Boolean addQuestion(AddQuestionForm addQuestionForm, Long userId);
    PageResult<QuestionVO> queryQuestion(QuestionQueryForm questionQueryForm, Long selfId);
    void addEsDoc(QuestionDoc questionDoc);
    void updateEsDoc(QuestionDoc questionDoc);

    Boolean deleteQuestion(Long questionId, Long selfId);

    void deleteEsDoc(Long id);

    SearchHits<QuestionDoc> queryEsQuestion(QuestionQueryForm questionQueryForm, Long selfId);
}
