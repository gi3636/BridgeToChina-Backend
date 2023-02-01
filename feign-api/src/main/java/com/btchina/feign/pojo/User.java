package com.btchina.feign.pojo;

import java.io.Serializable;
import java.util.Date;

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

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private String username;

    private String password;

    private String major;

    private String mobile;

    private String nickname;

    private String avatar;

    private Integer sex;

    private Date birthday;

    private String country;

    private String city;

    private String description;

    private String cover;

    private Date createdTime;

    private Date updatedTime;

    private Boolean deleted;


}
