package com.email.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

@Data
public class User extends Model<User> {
    //用户id
    private Integer id;
    //用户名
    private String username;
    //密码
    private String password;
    //电子邮箱地址
    private String email;
    //用户角色
    private String role;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

}

