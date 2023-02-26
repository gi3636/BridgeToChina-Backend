package com.btchina.comment.service.impl;

import com.btchina.comment.entity.Comment;
import com.btchina.comment.mapper.CommentMapper;
import com.btchina.comment.model.form.AddCommentForm;
import com.btchina.comment.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.core.exception.GlobalException;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论表 服务实现类
 * </p>
 *
 * @author franky
 * @since 2023-02-25
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public Boolean addComment(AddCommentForm addCommentForm, Long userId) {
        if (userId== null) {
            throw new GlobalException("用户未登录");
        }
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setAnswerId(addCommentForm.getAnswerId());
        comment.setContent(addCommentForm.getContent());
        comment.setToUserId(addCommentForm.getToUserId());
        comment.setParentId(addCommentForm.getParentId());
        comment.setLikeCount(0);
        Boolean isSuccess =this.save(comment);




        return null;
    }
}
