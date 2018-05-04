package com.sandman.download.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.ZonedDateTime;

/**
 * Created by wangj on 2018/5/4.
 */
public class UserDto {
    private Long id;
    private String userName;
    @JsonIgnore
    private String password;
    private String nickName;
    @JsonIgnore
    private String mobile;
    @JsonIgnore
    private String email;
    private String validateCode;
    private int gold;
    private int available;
    private Long createBy;
    private ZonedDateTime createTime;
    private Long updateBy;
    private ZonedDateTime updateTime;
    private int delFlag;

}
