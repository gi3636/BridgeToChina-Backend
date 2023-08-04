/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : localhost:3306
 Source Schema         : bridge_to_china

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 04/08/2023 12:17:19
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tbl_answer
-- ----------------------------
DROP TABLE IF EXISTS `tbl_answer`;
CREATE TABLE `tbl_answer` (
  `id` bigint NOT NULL COMMENT '回答id',
  `question_id` bigint NOT NULL COMMENT '问题id',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '回答内容',
  `is_best` int unsigned NOT NULL DEFAULT '0' COMMENT '是否最佳回答 1是 0不是',
  `use_count` int unsigned NOT NULL DEFAULT '0' COMMENT '采用数',
  `comment_count` int unsigned NOT NULL DEFAULT '0' COMMENT '评论数',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='回答表';

-- ----------------------------
-- Table structure for tbl_answer_user_use
-- ----------------------------
DROP TABLE IF EXISTS `tbl_answer_user_use`;
CREATE TABLE `tbl_answer_user_use` (
  `id` bigint NOT NULL COMMENT 'id',
  `answer_id` bigint NOT NULL COMMENT '回答id',
  `user_id` bigint NOT NULL COMMENT '用户Id',
  `status` int unsigned NOT NULL DEFAULT '0' COMMENT '状态 0是取消 1是采用',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户采用问题关系表';

-- ----------------------------
-- Table structure for tbl_category
-- ----------------------------
DROP TABLE IF EXISTS `tbl_category`;
CREATE TABLE `tbl_category` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '名称',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='类别表';

-- ----------------------------
-- Table structure for tbl_comment
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comment`;
CREATE TABLE `tbl_comment` (
  `id` bigint NOT NULL COMMENT '评论id',
  `answer_id` bigint NOT NULL COMMENT '回答id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `parent_id` bigint DEFAULT NULL COMMENT '回复的评论id',
  `to_user_id` bigint DEFAULT NULL COMMENT '回复的对象id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '评论内容',
  `like_count` int unsigned NOT NULL DEFAULT '0' COMMENT '评论点赞数',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论表';

-- ----------------------------
-- Table structure for tbl_comment_user_like
-- ----------------------------
DROP TABLE IF EXISTS `tbl_comment_user_like`;
CREATE TABLE `tbl_comment_user_like` (
  `id` bigint NOT NULL COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `comment_id` bigint NOT NULL COMMENT '评论id',
  `answer_id` bigint NOT NULL COMMENT '回答id',
  `status` int NOT NULL COMMENT '点赞状态 0是取消 1是点赞',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='评论点赞数表';

-- ----------------------------
-- Table structure for tbl_dialog
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dialog`;
CREATE TABLE `tbl_dialog` (
  `id` bigint NOT NULL COMMENT 'ID',
  `last_msg_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '最后接收消息ID',
  `chat_type` int NOT NULL COMMENT '聊天类型 1是私聊 2是群聊',
  `message_type` int NOT NULL COMMENT '消息类型 1是文本 2是图片 ',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '消息内容',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='会话详情表';

-- ----------------------------
-- Table structure for tbl_dialog_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_dialog_user`;
CREATE TABLE `tbl_dialog_user` (
  `id` bigint NOT NULL COMMENT 'ID',
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `to_user_id` bigint NOT NULL COMMENT '对方用户ID',
  `dialog_id` bigint NOT NULL COMMENT '会话ID',
  `unread_count` int DEFAULT NULL COMMENT '未读消息数',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户对话关系表';

-- ----------------------------
-- Table structure for tbl_message
-- ----------------------------
DROP TABLE IF EXISTS `tbl_message`;
CREATE TABLE `tbl_message` (
  `id` bigint NOT NULL COMMENT 'ID',
  `dialog_id` bigint NOT NULL COMMENT '会话ID',
  `msg_id` varchar(255) NOT NULL COMMENT '消息ID(前端消息ID)',
  `sender_id` bigint NOT NULL COMMENT '发送者ID',
  `receiver_id` bigint NOT NULL COMMENT '接收者ID',
  `chat_type` int NOT NULL COMMENT '聊天类型 1是私聊 2是群聊',
  `signed` tinyint DEFAULT NULL COMMENT '是否签收 1是已签收 , 0 是未签收',
  `message_type` int NOT NULL COMMENT '消息类型 1是文本 2是图片 ',
  `content` varchar(255) NOT NULL COMMENT '消息内容',
  `is_read` tinyint(1) NOT NULL COMMENT '是否已读 1是已读 , 0 是未读',
  `extend` varchar(255) DEFAULT NULL COMMENT '扩展',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息表';

-- ----------------------------
-- Table structure for tbl_news
-- ----------------------------
DROP TABLE IF EXISTS `tbl_news`;
CREATE TABLE `tbl_news` (
  `id` bigint NOT NULL COMMENT 'Id',
  `category_id` bigint DEFAULT NULL COMMENT '分类ID',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `is_top` tinyint(1) NOT NULL COMMENT '是否置顶 1是置顶 0是不置顶',
  `come_from` varchar(255) NOT NULL COMMENT '来源',
  `like_count` int DEFAULT '0' COMMENT '点赞数',
  `view_count` int DEFAULT '0' COMMENT '浏览数',
  `status` int DEFAULT '0' COMMENT '审核状态 0是未审核 1是审核通过 2是审核不通过',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='资讯表';

-- ----------------------------
-- Table structure for tbl_notify
-- ----------------------------
DROP TABLE IF EXISTS `tbl_notify`;
CREATE TABLE `tbl_notify` (
  `id` bigint NOT NULL COMMENT 'id',
  `sender_id` bigint NOT NULL COMMENT '操作者id, 0是系统发送',
  `receiver_id` bigint NOT NULL COMMENT '接收者id',
  `object_id` bigint NOT NULL COMMENT '对象id',
  `action_type` int NOT NULL COMMENT '操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注',
  `template_id` int NOT NULL COMMENT '消息模板id',
  `channel_type` int NOT NULL COMMENT '渠道类型 1 站内信 2短信 3邮箱',
  `object_type` int NOT NULL COMMENT '对象类型 1 问题 2 用户',
  `is_read` tinyint(1) DEFAULT NULL COMMENT '是否已读  1已读 0未读 ',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='消息通知表';

-- ----------------------------
-- Table structure for tbl_question
-- ----------------------------
DROP TABLE IF EXISTS `tbl_question`;
CREATE TABLE `tbl_question` (
  `id` bigint NOT NULL COMMENT 'Id',
  `user_id` bigint NOT NULL COMMENT '用户Id',
  `best_answer_id` bigint DEFAULT NULL COMMENT '最佳回答Id',
  `title` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '内容',
  `favorite_count` int NOT NULL DEFAULT '0' COMMENT '收藏数',
  `like_count` int unsigned NOT NULL DEFAULT '0' COMMENT '点赞数',
  `view_count` int unsigned NOT NULL DEFAULT '0' COMMENT '浏览数',
  `answer_count` int unsigned NOT NULL DEFAULT '0' COMMENT '回答数',
  `images` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci COMMENT '图片,多个图片用逗号分隔',
  `is_public` tinyint(1) unsigned zerofill DEFAULT NULL COMMENT '是否公开 1是公开，0是个人可见',
  `status` int DEFAULT '0' COMMENT '审核状态 0是未审核 1是审核通过 2是审核不通过',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='问题表';

-- ----------------------------
-- Table structure for tbl_question_user_favorite
-- ----------------------------
DROP TABLE IF EXISTS `tbl_question_user_favorite`;
CREATE TABLE `tbl_question_user_favorite` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `question_id` bigint NOT NULL COMMENT '问题Id',
  `status` int NOT NULL COMMENT '收藏状态 0是取消 1是收藏',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='问题收藏表';

-- ----------------------------
-- Table structure for tbl_question_user_like
-- ----------------------------
DROP TABLE IF EXISTS `tbl_question_user_like`;
CREATE TABLE `tbl_question_user_like` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `question_id` bigint NOT NULL COMMENT '问题id',
  `status` int NOT NULL COMMENT '点赞状态 0是取消 1是点赞',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='问题点赞表';

-- ----------------------------
-- Table structure for tbl_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_menu`;
CREATE TABLE `tbl_sys_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `parent_id` bigint DEFAULT NULL COMMENT '上级菜单',
  `title` varchar(200) DEFAULT NULL COMMENT '显示名称',
  `type` varchar(10) DEFAULT NULL COMMENT '类型',
  `name` varchar(100) DEFAULT NULL COMMENT '别名',
  `icon` varchar(100) DEFAULT NULL COMMENT '菜单图标',
  `path` varchar(100) DEFAULT NULL COMMENT '路由地址',
  `redirect` varchar(200) DEFAULT NULL COMMENT '重定向',
  `hidden` bit(1) DEFAULT b'0' COMMENT '隐藏菜单',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建者',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='菜单表';

-- ----------------------------
-- Table structure for tbl_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_role`;
CREATE TABLE `tbl_sys_role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `label` varchar(200) DEFAULT NULL COMMENT '角色名称',
  `alias` varchar(100) DEFAULT NULL COMMENT '角色别名',
  `sort` int DEFAULT '0' COMMENT '排序',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建者',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色表';

-- ----------------------------
-- Table structure for tbl_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_role_menu`;
CREATE TABLE `tbl_sys_role_menu` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `role_id` bigint DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='角色菜单表';

-- ----------------------------
-- Table structure for tbl_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_user`;
CREATE TABLE `tbl_sys_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `username` varchar(20) NOT NULL COMMENT '用户名',
  `password` varchar(200) NOT NULL COMMENT '密码',
  `salt` varchar(10) DEFAULT NULL COMMENT '盐',
  `name` varchar(64) DEFAULT NULL COMMENT '姓名',
  `created_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `created_by` bigint DEFAULT NULL COMMENT '创建者',
  `updated_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `updated_by` bigint DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3 COMMENT='用户账户';

-- ----------------------------
-- Table structure for tbl_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `tbl_sys_user_role`;
CREATE TABLE `tbl_sys_user_role` (
  `id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `role_id` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='用户角色表';

-- ----------------------------
-- Table structure for tbl_tag
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tag`;
CREATE TABLE `tbl_tag` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `name` varchar(255) NOT NULL COMMENT '标签内容',
  `count` int unsigned NOT NULL COMMENT '标签被引用次数',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=91 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签表';

-- ----------------------------
-- Table structure for tbl_tag_question
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tag_question`;
CREATE TABLE `tbl_tag_question` (
  `id` bigint NOT NULL,
  `tag_id` bigint NOT NULL COMMENT '标签id',
  `question_id` bigint NOT NULL COMMENT '问题Id',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签问题表';

-- ----------------------------
-- Table structure for tbl_tag_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_tag_user`;
CREATE TABLE `tbl_tag_user` (
  `id` bigint NOT NULL,
  `tag_id` bigint NOT NULL COMMENT '标签id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='标签用户表';

-- ----------------------------
-- Table structure for tbl_user
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户',
  `username` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '密码',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '标签',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '手机号',
  `nickname` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '昵称;昵称',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '头像',
  `fans_count` int unsigned NOT NULL DEFAULT '0' COMMENT '粉丝数',
  `follow_count` int unsigned NOT NULL DEFAULT '0' COMMENT '关注数',
  `sex` int DEFAULT '0' COMMENT '性别 0是保密 1是男 2是女',
  `country` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '国家',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '城市',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '简介',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL COMMENT '个人介绍的背景图',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否删除;1是删除，0是不删除',
  PRIMARY KEY (`id`,`avatar`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户表';

-- ----------------------------
-- Table structure for tbl_user_action
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_action`;
CREATE TABLE `tbl_user_action` (
  `id` bigint NOT NULL COMMENT 'id',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `object_id` bigint NOT NULL COMMENT '对象id',
  `action_type` int DEFAULT NULL COMMENT '操作类型 1 点赞 2 收藏 3 评论 4 采用 5 回答 6 提问 7关注',
  `object_type` int DEFAULT NULL COMMENT '对象类型 1 问题 2 用户',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户动态表';

-- ----------------------------
-- Table structure for tbl_user_follow
-- ----------------------------
DROP TABLE IF EXISTS `tbl_user_follow`;
CREATE TABLE `tbl_user_follow` (
  `id` bigint NOT NULL,
  `user_id` bigint NOT NULL COMMENT '用户id',
  `follow_user_id` bigint NOT NULL COMMENT '关注的人id',
  `status` int NOT NULL COMMENT '关注状态 0是取消 1是关注',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='关注表';

SET FOREIGN_KEY_CHECKS = 1;
