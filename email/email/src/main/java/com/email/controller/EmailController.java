package com.email.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.email.entity.Email;
import com.email.entity.User;
import com.email.service.EmailService;
import com.email.utils.R;
import com.fasterxml.jackson.databind.DatabindContext;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/email")
public class EmailController{
    /**
     * 服务对象
     */
    @Autowired
    private EmailService emailService;

    @GetMapping("/getSpp")
    public R getSpp() {
        System.out.println("成功");

        return new R();
    }


    //垃圾箱
    @GetMapping("/getspam")
    public R getSpam(@RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "10") int size,
                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user!=null){
            IPage<Email> spam = emailService.getSpam(new Page<>(page, size), user);
            session.setAttribute("state","spam");


            return new R(true,spam);
        }else {
            return new R(false);
        }

    }
//发
    @GetMapping("/getsent")
    public R getSent(@RequestParam(required = false, defaultValue = "1") int page,
                                @RequestParam(required = false, defaultValue = "10") int size,
                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user!=null){
            IPage<Email> sent = emailService.getSent(new Page<>(page, size), user);
            session.setAttribute("state","send");
            return new R(true,sent);
        }else {
            return null;
        }
    }
//收
    @GetMapping("/getinbox")
    public R getInbox(@RequestParam(required = false, defaultValue = "1") int page,
                                 @RequestParam(required = false, defaultValue = "10") int size,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        System.out.println(user.toString());
        if (user!=null){
            IPage<Email> inbox = emailService.getInbox(new Page<>(page, size), user);
            session.setAttribute("state","inbox");
            System.out.println(inbox.getRecords());
            return new R(true,inbox);
        }else {
            return new R(false);
        }

    }

    @GetMapping("/getdraft")
    public R getDraft(@RequestParam(required = false, defaultValue = "1") int page,
                                 @RequestParam(required = false, defaultValue = "10") int size,
                                 HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user!=null){
            session.setAttribute("state","draft");
            return new R(true,emailService.getDraft(new Page<>(page, size),user));
        }else {
            return new R(false);
        }

    }

    @PostMapping("/send")
    public R send(@RequestBody Email email,HttpSession session) {
        System.out.println(email.toString());
        email.setisDraft(false);
        email.setisSpam(false);
        email.setisRead(false);

        User user = (User) session.getAttribute("user");
        if (emailService.send(email,user)){
            return new R(true);
        }else {
            return new R(false);
        }
    }

    @PostMapping("/createdraft")
    public R createDraft(@RequestBody Email email,HttpSession session) {
        System.out.println(email.toString());
        email.setisDraft(true);
        email.setisSpam(false);
        email.setisRead(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return new R(false);
        }
        if (emailService.draft(email,user)){
            return new R(true);
        }else {
            return new R(false);
        }
    }


    @DeleteMapping("/deletedemail/{id}")
    public R deleteemail(@PathVariable Integer id,HttpSession session){
        System.out.println(id);
        String state = (String) session.getAttribute("state");
        Email email = emailService.getById(id);
        System.out.println(email.toString());
        switch (state){
            case "draft":
            case "spam":
                return new R(emailService.removeById(id));
            case "inbox":
                email.setisRecipient(false);
                return new R(emailService.updateById(email));
            case "send":
                email.setisSend(false);
                return new R(emailService.updateById(email));
        }
        return new R(false);
    }


    @PostMapping("/sendqq")
    public R sendqq(@RequestBody Email email,HttpSession session) {
        email.setisDraft(false);
        email.setisSpam(false);
        email.setisRead(false);

        User user = (User) session.getAttribute("user");
        if (emailService.sendqq(email,user)){
            return new R(true);
        }else {
            return new R(false);
        }
    }

}

