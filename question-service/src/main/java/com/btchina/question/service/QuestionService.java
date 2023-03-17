package com.btchina.question.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.core.api.PageResult;
import com.btchina.question.entity.Question;
import com.btchina.question.model.doc.QuestionDoc;
import com.btchina.question.model.form.*;
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
    PageResult<QuestionVO> queryQuestion(QuestionQueryForm questionQueryForm, Long selfId,Boolean isSeo);

    SearchHits<QuestionDoc> queryEsQuestion(QuestionQueryForm questionQueryForm, Long selfId);
    Boolean deleteQuestion(Long questionId, Long selfId);

    void addEsDoc(QuestionDoc questionDoc);
    void updateEsDoc(QuestionDoc questionDoc);

    void deleteEsDoc(Long id);

    void updateFieldEsDoc(Long id, String field, Object value);

    QuestionDoc getEsDoc(Long id);


    Boolean editQuestion(EditQuestionForm editQuestionForm, Long selfId);

    void increaseAnswerCount(Long questionId);

    void decreaseAnswerCount(Long questionId);

    Boolean setBestAnswer(QuestionSetAnswerForm questionSetAnswerForm, Long userId);

    QuestionVO getVObyId(Long id, Long userId);

    PageResult<QuestionVO> searchQuestion(QuestionSearchForm questionSearchForm, Long userId);

    void increaseFavouriteCount(Long questionId);

    void decreaseFavouriteCount(Long questionId);

    Boolean addView(Long id);

    Boolean cancelBestAnswer(QuestionSetAnswerForm questionSetAnswerForm, Long userId);

    String generateTitle(String text);

}
