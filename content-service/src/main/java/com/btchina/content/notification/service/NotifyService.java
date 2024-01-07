package com.btchina.content.notification.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.btchina.content.notification.feign.qo.NotifyQueryForm;
import com.btchina.content.notification.feign.qo.NotifyReadAllForm;
import com.btchina.content.notification.feign.qo.NotifyReadForm;
import com.btchina.content.notification.feign.vo.NotifyVO;
import com.btchina.content.notification.mapper.NotifyMapper;
import com.btchina.content.notification.model.Notify;
import com.btchina.content.question.feign.QuestionClient;
import com.btchina.content.question.feign.vo.QuestionVO;
import com.btchina.core.api.PageResult;
import com.btchina.core.api.ResultCode;
import com.btchina.core.exception.GlobalException;
import com.btchina.message.constant.NotifyConstant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.btchina.message.feign.qo.NotifyAddForm;
import com.btchina.model.enums.ActionEnum;
import com.btchina.model.enums.ObjectEnum;
import com.btchina.user.feign.UserClient;
import com.btchina.user.feign.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * <p>
 * 消息通知表 服务类
 * </p>
 *
 * @author franky
 * @since 2023-03-30
 */
public interface NotifyService extends IService<Notify> {

    Boolean add(NotifyAddForm notifyAddForm);

    PageResult<NotifyVO> list(Long userId, NotifyQueryForm notifyQueryForm);

    Boolean read(Long userId, NotifyReadForm notifyReadForm);

    Boolean readAll(Long userId, NotifyReadAllForm notifyReadAllForm);

    List<NotifyVO> convertToNotifyVOList(List<Notify> notifyList);

    /**
     * <p>
     * 消息通知表 服务实现类
     * </p>
     *
     * @author franky
     * @since 2023-03-30
     */
    @Service
    @Slf4j
    class NotifyServiceImpl extends ServiceImpl<NotifyMapper, Notify> implements NotifyService {


        @Autowired
        private RabbitTemplate rabbitTemplate;

        @Autowired
        private UserClient userClient;

        @Autowired
        private QuestionClient questionClient;

        @Override
        public Boolean add(NotifyAddForm notifyAddForm) {
            Notify notify = new Notify();
            BeanUtils.copyProperties(notifyAddForm, notify);
            // 查询数据是否存在
            LambdaQueryWrapper<Notify> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Notify::getReceiverId, notifyAddForm.getReceiverId());
            queryWrapper.eq(Notify::getObjectType, notifyAddForm.getObjectType());
            queryWrapper.eq(Notify::getObjectId, notifyAddForm.getObjectId());
            Notify notifyDb = this.getOne(queryWrapper);
            log.info("notifyDb:{}", notifyDb);
            // 如果存在则不保存
            if (notifyDb != null) {
                return false;
            }
            // 保存到数据库
            Boolean isSuccess = this.save(notify);
            // 发送消息通知
            if (isSuccess) {
                rabbitTemplate.convertAndSend(NotifyConstant.EXCHANGE_NAME, NotifyConstant.PUSH_KEY, notify);
            }

            return isSuccess;
        }

        @Override
        public PageResult<NotifyVO> list(Long userId, NotifyQueryForm notifyQueryForm) {
            if (userId == null) {
                throw GlobalException.from(ResultCode.UNAUTHORIZED);
            }
            LambdaQueryWrapper<Notify> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Notify::getReceiverId, userId);
            if (notifyQueryForm.getIsRead() != null) {
                queryWrapper.eq(Notify::getIsRead, notifyQueryForm.getIsRead());
            }
            if (notifyQueryForm.getChannelType() != null) {
                queryWrapper.eq(Notify::getChannelType, notifyQueryForm.getChannelType());
            }
            // 按照时间倒序
            queryWrapper.orderByDesc(Notify::getCreatedTime);

            Page<Notify> page = new Page<>(notifyQueryForm.getCurrentPage(), notifyQueryForm.getPageSize());
            Page<Notify> notifyPage = this.page(page, queryWrapper);
            PageResult<NotifyVO> pageResult = new PageResult<>();
            List<Notify> notifyList = notifyPage.getRecords();

            // 转换为NotifyVO列表
            List<NotifyVO> notifyVOList = convertToNotifyVOList(notifyList);

            // 设置分页信息
            pageResult.setTotal(notifyPage.getTotal());
            pageResult.setList(notifyVOList);
            pageResult.setTotalPage((int) notifyPage.getPages());
            pageResult.setCurrentPage(notifyQueryForm.getCurrentPage());
            pageResult.setPageSize(notifyQueryForm.getPageSize());
            return pageResult;
        }

        /**
         * 转换通知内容
         */
        @Override
        public List<NotifyVO> convertToNotifyVOList(List<Notify> notifyList) {
            List<NotifyVO> notifyVOList = new ArrayList<>();
            //获取用户信息
            List<Long> userIds = new ArrayList<>();
            List<Long> questionIds = new ArrayList<>();
            for (Notify notify : notifyList) {
                userIds.add(notify.getSenderId());
                // 如果是问题通知，则需要查询问题信息
                if (notify.getObjectType() == 1) {
                    questionIds.add(notify.getObjectId());
                }
            }
            Map<Long, UserVO> userVOMap = userClient.findByIds(userIds);
            Map<Long, QuestionVO> questionVOMap = questionClient.findByIds(questionIds);
            // 转换为VO
            for (Notify notify : notifyList) {
                NotifyVO notifyVO = new NotifyVO();
                BeanUtils.copyProperties(notify, notifyVO);
                notifyVO.setActionName(ActionEnum.getActionEnum(notify.getActionType()).getContent());
                notifyVO.setSenderName(userVOMap.get(notify.getSenderId()).getNickname());
                notifyVO.setSenderAvatar(userVOMap.get(notify.getSenderId()).getAvatar());
                notifyVO.setContent(convertNotifyContent(notifyVO));
                notifyVO.setQuestion(questionVOMap.get(notify.getObjectId()));
                notifyVOList.add(notifyVO);
            }
            return notifyVOList;
        }

        public String convertNotifyContent(NotifyVO notify) {
            String content = "";
            switch (ObjectEnum.geObjectEnum(notify.getObjectType())) {
                case QUESTION:
                    switch (ActionEnum.getActionEnum(notify.getActionType())) {
                        case ANSWER:
                            content = notify.getSenderName() + "回答了你的问题";
                            break;
                        case FAVORITE:
                            content = notify.getSenderName() + "收藏了你的问题";
                            break;
                        case LIKE:
                            content = notify.getSenderName() + "赞了你的问题";
                            break;
                        default:
                            break;
                    }
                    break;
                case USER:
                    switch (ActionEnum.getActionEnum(notify.getActionType())) {
                        case FOLLOW:
                            content = notify.getSenderName() + "关注了你";
                            break;
                        default:
                            break;
                    }
                    break;
            }
            return content;
        }


        @Override
        public Boolean read(Long userId, NotifyReadForm notifyReadForm) {
            if (userId == null) {
                throw GlobalException.from(ResultCode.UNAUTHORIZED);
            }
            LambdaQueryWrapper<Notify> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Notify::getId, notifyReadForm.getId());
            Notify notify = this.baseMapper.selectOne(queryWrapper);
            if (notify == null) {
                throw GlobalException.from(ResultCode.NOTIFY_NOT_EXIST);
            }
            //判断是否有权限修改
            if (!notify.getReceiverId().equals(userId)) {
                throw GlobalException.from(ResultCode.FORBIDDEN);
            }
            notify.setIsRead(true);
            notify.setReadTime(new Date());
            this.baseMapper.updateById(notify);
            return true;
        }

        @Override
        public Boolean readAll(Long userId, NotifyReadAllForm notifyReadAllForm) {
            if (userId == null) {
                throw GlobalException.from(ResultCode.UNAUTHORIZED);
            }

            List<Notify> notifies = this.baseMapper.selectBatchIds(Arrays.asList(notifyReadAllForm.getIds()));
            if (notifies == null) {
                throw GlobalException.from(ResultCode.NOTIFY_NOT_EXIST);
            }
            //判断是否有权限修改
            if (!notifies.get(0).getReceiverId().equals(userId)) {
                throw GlobalException.from(ResultCode.FORBIDDEN);
            }
            for (Notify notify : notifies) {
                notify.setIsRead(true);
                notify.setReadTime(new Date());
            }
            this.baseMapper.batchUpdate(notifies,true,new Date());
            return true;
        }




    }
}
