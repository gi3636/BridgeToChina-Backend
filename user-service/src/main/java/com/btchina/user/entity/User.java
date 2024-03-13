package com.btchina.user.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;


import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author franky
 * @since 2023-01-30
 */
@Getter
@Setter
@TableName("tbl_user")
@Tag(name = "User对象", description = "用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description ="用户")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description ="用户名")
    private String username;

    @Schema(description ="密码")
    private String password;

    @Schema(description ="标签")
    private String tags;

    @Schema(description ="关注数")
    private Integer followCount;

    @Schema(description ="粉丝数")
    private Integer fansCount;

    @Schema(description ="手机号")
    private String mobile;

    @Schema(description ="昵称;昵称")
    private String nickname;

    @Schema(description ="头像")
    private String avatar;

    @Schema(description ="性别 0是保密 1是男 2是女")
    private Integer sex;

    @Schema(description ="国家")
    private String country;

    @Schema(description ="城市")
    private String city;

    @Schema(description ="简介")
    private String description;

    @Schema(description ="个人介绍的背景图")
    private String cover;

    @Schema(description ="创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date createdTime;

    @Schema(description ="更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updatedTime;

    @Schema(description ="是否删除;1是删除，0是不删除")
    @TableLogic
    private Boolean deleted;


}
