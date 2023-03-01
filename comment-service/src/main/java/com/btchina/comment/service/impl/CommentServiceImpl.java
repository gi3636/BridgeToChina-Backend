package com.btchina.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.comment.constant.CommentConstant;
import com.btchina.comment.entity.Comment;
import com.btchina.comment.entity.CommentUserLike;
import com.btchina.comment.mapper.CommentMapper;
import com.btchina.comment.model.form.AddCommentForm;
import com.btchina.comment.model.form.CommentLikeForm;
import com.btchina.comment.model.form.QueryCommentForm;
import com.btchina.comment.model.form.UpdateCommentForm;
import com.btchina.comment.model.vo.CommentVO;
import com.btchina.comment.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.comment.service.CommentUserLikeService;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import com.btchina.core.exception.GlobalException;
import com.btchina.entity.Answer;
import com.btchina.feign.clients.QuestionClient;
import com.btchina.feign.clients.UserClient;
import com.btchina.model.vo.user.UserVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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


    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private QuestionClient questionClient;

    @Autowired
    private UserClient userClient;

    @Autowired
    private CommentUserLikeService commentUserLikeService;


    @Override
    public Boolean addComment(AddCommentForm addCommentForm, Long userId) {
        if (userId == null) {
            throw new GlobalException("用户未登录");
        }
        Answer answer = questionClient.findAnswerById(addCommentForm.getAnswerId());
        if (answer == null) {
            throw new GlobalException("回答不存在");
        }
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setAnswerId(addCommentForm.getAnswerId());
        comment.setContent(addCommentForm.getContent());
        comment.setToUserId(addCommentForm.getToUserId());
        comment.setParentId(addCommentForm.getParentId());
        comment.setLikeCount(0);
        Boolean isSuccess = this.save(comment);
        if (isSuccess) {
            rabbitTemplate.convertAndSend(CommentConstant.EXCHANGE_NAME, CommentConstant.INSERT_KEY, comment);
        }
        return isSuccess;
    }

    @Override
    public Boolean delComment(DeleteForm deleteForm, Long userId) {
        if (userId == null) {
            throw new GlobalException("用户未登录");
        }
        Comment comment = this.getById(deleteForm.getId());
        if (comment == null) {
            throw new GlobalException("评论不存在");
        }
        if (!comment.getUserId().equals(userId)) {
            throw new GlobalException("无权限删除");
        }
        Boolean isSuccess = this.removeById(deleteForm.getId());
        if (isSuccess) {
            rabbitTemplate.convertAndSend(CommentConstant.EXCHANGE_NAME, CommentConstant.DELETE_KEY, comment);
        }
        return isSuccess;
    }

    @Override
    public Boolean updateComment(UpdateCommentForm updateCommentForm, Long userId) {
        if (userId == null) {
            throw new GlobalException("用户未登录");
        }
        Comment comment = this.getById(updateCommentForm.getId());
        if (comment == null) {
            throw new GlobalException("评论不存在");
        }
        if (!comment.getUserId().equals(userId)) {
            throw new GlobalException("无权限修改");
        }
        comment.setContent(updateCommentForm.getContent());
        return this.updateById(comment);
    }

    @Override
    public PageResult<CommentVO> queryCommentList(QueryCommentForm queryCommentForm, Long userId) {
        Page<Comment> page = new Page<>(queryCommentForm.getCurrentPage(), queryCommentForm.getPageSize());
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getAnswerId, queryCommentForm.getAnswerId());
        Page<Comment> commentPage = baseMapper.selectPage(page, queryWrapper);
        List<Comment> commentList = commentPage.getRecords();
        List<Long> userIds = commentList.stream().map(comment -> {
            List<Long> ids = new ArrayList<>();
            ids.add(comment.getUserId());
            ids.add(comment.getToUserId());
            return ids;
        }).flatMap(List::stream).collect(Collectors.toList());
        Map<Long, UserVO> userMap = userClient.findByIds(userIds);
        List<CommentVO> commentVOList = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            commentVO.setNickname(userMap.get(comment.getUserId()).getNickname());
            commentVO.setAvatar(userMap.get(comment.getUserId()).getAvatar());
            if (comment.getToUserId() != null) {
                commentVO.setToNickname(userMap.get(comment.getToUserId()).getNickname());
            }

            // 查询点赞状态 userID为空则为未登录
            if (userId != null) {
                CommentUserLike commentUserLike = commentUserLikeService.getOne(new LambdaQueryWrapper<CommentUserLike>()
                        .eq(CommentUserLike::getCommentId, comment.getId())
                        .eq(CommentUserLike::getUserId, userId));
                if (commentUserLike != null) {
                    commentVO.setLikeStatus(commentUserLike.getStatus());
                }
            }
            commentVOList.add(commentVO);
        }

        PageResult<CommentVO> pageResult = new PageResult<>();
        pageResult.setTotal(commentPage.getTotal());
        pageResult.setList(commentVOList);
        pageResult.setTotalPage((int) commentPage.getPages());
        pageResult.setCurrentPage((int) commentPage.getCurrent());
        pageResult.setPageSize((int) commentPage.getSize());
        return pageResult;
    }

    @Override
    public Boolean like(CommentLikeForm commentLikeForm, Long userId) {
        if (userId == null) {
            throw new GlobalException("用户未登录");
        }
        Comment comment = this.getById(commentLikeForm.getId());
        if (comment == null) {
            throw new GlobalException("评论不存在");
        }
        Boolean isSuccess = false;
        CommentUserLike commentUserLike = commentUserLikeService.getOne(new LambdaQueryWrapper<CommentUserLike>()
                .eq(CommentUserLike::getCommentId, commentLikeForm.getId())
                .eq(CommentUserLike::getUserId, userId));

        if (commentUserLike == null) {
            // 如果没有采用过，则新增一条记录
            commentUserLike = new CommentUserLike();
            commentUserLike.setUserId(userId);
            commentUserLike.setAnswerId(comment.getAnswerId());
            commentUserLike.setCommentId(commentLikeForm.getId());
            commentUserLike.setStatus(commentLikeForm.getStatus());
            isSuccess = commentUserLikeService.save(commentUserLike);
        } else {
            // 如果已经采用过，则判断状态
            if (Objects.equals(commentUserLike.getStatus(), commentLikeForm.getStatus())) {
                return true;
            }
            // 如果已经采用过，则更新状态
            commentUserLike.setStatus(commentLikeForm.getStatus());
            isSuccess = commentUserLikeService.updateById(commentUserLike);
        }
        if (commentUserLike.getStatus() == 1) {
            incLikeCount(comment.getId());
        } else {
            decLikeCount(comment.getId());
        }
        return isSuccess;
    }

    private void decLikeCount(Long id) {
        Comment comment = this.getById(id);
        comment.setLikeCount(comment.getLikeCount() - 1);
        this.updateById(comment);
    }

    private void incLikeCount(Long id) {
        Comment comment = this.getById(id);
        comment.setLikeCount(comment.getLikeCount() + 1);
        this.updateById(comment);
    }
}
