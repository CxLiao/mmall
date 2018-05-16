package com.mmall.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
/**
 * User domain
 * @author liaocx on 2017/10/13.
 * @author liaocx 重写于2018/5/15 新增lombok注解
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String question;

    private String answer;

    private Integer role;

    private Date createTime;

    private Date updateTime;
}