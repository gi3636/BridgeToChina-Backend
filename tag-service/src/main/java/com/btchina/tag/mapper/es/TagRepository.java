package com.btchina.tag.mapper.es;

import com.btchina.tag.model.doc.TagDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * TagRepository
 * 提供save、findById、findAll、count、delete、exists等接口
 */
public interface TagRepository extends ElasticsearchRepository<TagDoc, Long> {
	TagDoc findById(String id);

}
