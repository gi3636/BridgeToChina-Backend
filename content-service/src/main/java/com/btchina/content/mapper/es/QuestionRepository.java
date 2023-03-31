package com.btchina.content.mapper.es;

import com.btchina.content.model.doc.QuestionDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * QuestionRepository
 * 提供save、findById、findAll、count、delete、exists等接口
 */
public interface QuestionRepository extends ElasticsearchRepository<QuestionDoc, Long> {

	/**
	 * 通过描述内容来搜索博客
	 * <br><br>
	 * 这里通过 @Highlight 完成了对高亮的需求，
	 * 其中 requireFieldMatch 参数是取消了只有字段匹配才有高亮的规则
	 * <br>
	 * 并通过 Pageable 和 SearchPage 完成了对分页的需求
	 * <br>
	 * 得到结果后仅需将分页的内容替换掉实体类的内容即可，已经是封装好的了
	 *
	 * @param descriptiveContent 描述语句
	 * @param pageable           分页
	 * @return 博客列表
	 */
	//@SuppressWarnings("SpringDataRepositoryMethodReturnTypeInspection")
	//SearchPage<BlogDoc> findByDescriptiveContent(String descriptiveContent, Pageable pageable);

	QuestionDoc findById(String id);



}
