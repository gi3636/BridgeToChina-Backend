package com.btchina.content.mapper.es;

import com.btchina.content.model.doc.QuestionDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * QuestionRepository
 * 提供save、findById、findAll、count、delete、exists等接口
 */
public interface QuestionRepository extends ElasticsearchRepository<QuestionDoc, Long> {


	QuestionDoc findById(String id);

	List<QuestionDoc> findAllByIdIn(List<String> ids);



}
