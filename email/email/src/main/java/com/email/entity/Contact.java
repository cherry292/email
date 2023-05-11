package com.email.entity;

import java.util.Date;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.io.Serializable;


public class Contact {
    //联系人id
    private Integer id;
    //用户id
    private Integer userId;
    //名称
    private String name;
    //电子邮件地址
    private String email;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;

}

