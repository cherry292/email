package com.email.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.email.entity.Contact;
import com.email.service.ContactService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;


@RestController
@RequestMapping("contact")
public class ContactController{
    /**
     * 服务对象
     */
    @Resource
    private ContactService contactService;


}

