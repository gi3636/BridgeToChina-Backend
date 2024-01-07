package com.btchina.content.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.btchina.content.infra.constant.CommentConstant;
import com.btchina.content.comment.model.entity.Comment;
import com.btchina.content.comment.model.entity.CommentUserLike;
import com.btchina.content.comment.mapper.CommentMapper;
import com.btchina.content.comment.feign.qo.CommentAddQO;
import com.btchina.content.comment.feign.qo.CommentLikeQO;
import com.btchina.content.comment.feign.qo.CommentQueryQO;
import com.btchina.content.comment.feign.qo.CommentUpdateQO;
import com.btchina.content.comment.feign.vo.CommentVO;
import com.btchina.content.comment.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.content.comment.service.CommentUserLikeService;
import com.btchina.content.question.model.Answer;
import com.btchina.core.api.DeleteForm;
import com.btchina.core.api.PageResult;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.content.question.feign.QuestionClient;
import com.btchina.user.feign.UserClient;
import com.btchina.user.feign.vo.UserVO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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

    @Resource
    private UserClient userClient;

    @Autowired
    private CommentUserLikeService commentUserLikeService;


    @Override
    public Boolean addComment(CommentAddQO commentAddQO, Long userId) {
        if (userId == null) {
            throw new GlobalException(ResultCode.UNAUTHORIZED);
        }
        Answer answer = questionClient.findAnswerById(commentAddQO.getAnswerId());
        if (answer == null) {
            throw new GlobalException("回答不存在");
        }
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setAnswerId(commentAddQO.getAnswerId());
        comment.setContent(commentAddQO.getContent());
        comment.setToUserId(commentAddQO.getToUserId());
        comment.setParentId(commentAddQO.getParentId());
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
            throw new GlobalException(ResultCode.UNAUTHORIZED);
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
    public Boolean updateComment(CommentUpdateQO commentUpdateQO, Long userId) {
        if (userId == null) {
            throw new GlobalException(ResultCode.UNAUTHORIZED);
        }
        Comment comment = this.getById(commentUpdateQO.getId());
        if (comment == null) {
            throw new GlobalException("评论不存在");
        }
        if (!comment.getUserId().equals(userId)) {
            throw new GlobalException("无权限修改");
        }
        comment.setContent(commentUpdateQO.getContent());
        return this.updateById(comment);
    }

    @Override
    public PageResult<CommentVO> queryCommentList(CommentQueryQO commentQueryQO, Long userId) {
        Page<Comment> page = new Page<>(commentQueryQO.getCurrentPage(), commentQueryQO.getPageSize());
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getAnswerId, commentQueryQO.getAnswerId());
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
    public Boolean like(CommentLikeQO commentLikeQO, Long userId) {
        if (userId == null) {
            throw new GlobalException(ResultCode.UNAUTHORIZED);
        }
        Comment comment = this.getById(commentLikeQO.getId());
        if (comment == null) {
            throw new GlobalException("评论不存在");
        }
        Boolean isSuccess = false;
        CommentUserLike commentUserLike = commentUserLikeService.getOne(new LambdaQueryWrapper<CommentUserLike>()
                .eq(CommentUserLike::getCommentId, commentLikeQO.getId())
                .eq(CommentUserLike::getUserId, userId));

        if (commentUserLike == null) {
            // 如果没有采用过，则新增一条记录
            commentUserLike = new CommentUserLike();
            commentUserLike.setUserId(userId);
            commentUserLike.setAnswerId(comment.getAnswerId());
            commentUserLike.setCommentId(commentLikeQO.getId());
            commentUserLike.setStatus(commentLikeQO.getStatus());
            isSuccess = commentUserLikeService.save(commentUserLike);
        } else {
            // 如果已经采用过，则判断状态
            if (Objects.equals(commentUserLike.getStatus(), commentLikeQO.getStatus())) {
                return true;
            }
            // 如果已经采用过，则更新状态
            commentUserLike.setStatus(commentLikeQO.getStatus());
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
