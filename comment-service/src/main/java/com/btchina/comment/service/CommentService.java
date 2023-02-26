package com.btchina.comment.service;

import com.btchina.comment.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.comment.model.form.AddCommentForm;

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
}
