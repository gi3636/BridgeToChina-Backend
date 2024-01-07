package com.btchina.content.comment.service;

import com.btchina.content.comment.model.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.content.comment.feign.qo.CommentAddQO;
import com.btchina.content.comment.feign.qo.CommentLikeQO;
import com.btchina.content.comment.feign.qo.CommentQueryQO;
import com.btchina.content.comment.feign.qo.CommentUpdateQO;
import com.btchina.content.comment.feign.vo.CommentVO;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;

/**
 * <p>
 * 评论表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
public interface CommentService extends IService<Comment> {

    Boolean addComment(CommentAddQO commentAddQO, Long userId);

    Boolean delComment(DeleteForm deleteForm, Long userId);

    Boolean updateComment(CommentUpdateQO commentUpdateQO, Long userId);

    PageResult<CommentVO> queryCommentList(CommentQueryQO commentQueryQO, Long userId);

    Boolean like(CommentLikeQO commentLikeQO, Long userId);
}
