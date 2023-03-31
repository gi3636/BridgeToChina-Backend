package com.btchina.content.service;

import com.btchina.content.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.content.model.form.AddCommentForm;
import com.btchina.content.model.form.CommentLikeForm;
import com.btchina.content.model.form.QueryCommentForm;
import com.btchina.content.model.form.UpdateCommentForm;
import com.btchina.content.model.vo.CommentVO;
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

    Boolean addComment(AddCommentForm addCommentForm, Long userId);

    Boolean delComment(DeleteForm deleteForm, Long userId);

    Boolean updateComment(UpdateCommentForm updateCommentForm, Long userId);

    PageResult<CommentVO> queryCommentList(QueryCommentForm queryCommentForm, Long userId);

    Boolean like(CommentLikeForm commentLikeForm, Long userId);
}
