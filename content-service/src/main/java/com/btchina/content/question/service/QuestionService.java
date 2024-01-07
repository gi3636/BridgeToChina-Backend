package com.btchina.content.question.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.btchina.content.question.feign.qo.*;
import com.btchina.core.api.PageResult;
import com.btchina.content.question.model.Question;
import com.btchina.content.question.model.doc.QuestionDoc;
import com.btchina.content.question.feign.vo.QuestionVO;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 问答表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-02-01
 */
public interface QuestionService extends IService<Question> {

    Boolean addQuestion(QuestionAddQO questionAddQO, Long userId);
    PageResult<QuestionVO> queryQuestion(QuestionQueryQO questionQueryQO, Long selfId, Boolean isSeo);

    SearchHits<QuestionDoc> queryEsQuestion(QuestionQueryQO questionQueryQO, Long selfId);
    Boolean deleteQuestion(Long questionId, Long selfId);

    void addEsDoc(QuestionDoc questionDoc);
    void updateEsDoc(QuestionDoc questionDoc);

    void deleteEsDoc(Long id);

    void updateFieldEsDoc(Long id, String field, Object value);

    QuestionDoc getEsDoc(Long id);


    Boolean editQuestion(QuestionEditQO editQuestionForm, Long selfId);

    void increaseAnswerCount(Long questionId);

    void decreaseAnswerCount(Long questionId);

    Boolean setBestAnswer(QuestionBestAnswerQO questionBestAnswerQO, Long userId);

    QuestionVO getVObyId(Long id, Long userId);

    PageResult<QuestionVO> searchQuestion(QuestionSearchQO questionSearchQO, Long userId);

    void increaseFavouriteCount(Long questionId);

    void decreaseFavouriteCount(Long questionId);

    Boolean addView(Long id);

    Boolean cancelBestAnswer(QuestionBestAnswerQO questionBestAnswerQO, Long userId);

    String generateTitle(String text);

    PageResult<QuestionVO> relatedQuestion(QuestionRelatedQO questionRelatedQO);

    Map<Long, QuestionVO> findByIds(List<Long> ids);

}
