package com.email.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.email.entity.Email;
import com.email.entity.User;
import com.email.service.UserService;
import com.email.utils.R;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/user")
public class UserController{
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;
    @PostMapping("/register")
    public R registerUser(@RequestBody User user) {
        System.out.println(user.toString());
        // 验证用户名是否已存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", user.getEmail());
        User existingUser = userService.getOne(queryWrapper);
        if (existingUser != null) {
            return new R(false,"邮箱已存在");
        }
        user.setUsername(user.getEmail());
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        user.setRole("用户");
        user.setCreateTime(date);
        user.setUpdateTime(date);
        // 创建新用户
        return new R(userService.save(user));
    }


    /**
     * 用户登录
     */
    @PostMapping("/login")
    public R loginUser(@RequestBody User user,HttpSession session) {
        String email = user.getEmail();
        System.out.println(user.toString());
        String password = user.getPassword();
        // 根据用户名从数据库中查询用户信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        User dbUser = userService.getOne(queryWrapper);
        if (dbUser!= null && dbUser.getPassword().equals(user.getPassword())) {
            session.setAttribute("user", dbUser);
            return new R(true);
        }else{
            return new R(false);
        }




    }
    /**
     * 查询用户信息
     */
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable HttpSession session) {
        // 验证用户是否已登录
        User user = (User) session.getAttribute("user");
        if (user  == null) {
            return null;
        }else{
            return user;
        }
    }


    @GetMapping("/getusername")
    public R getUsername(HttpSession session) {
        // 验证用户是否已登录
        User user = (User) session.getAttribute("user");
        if (user  == null) {
            return new R(false);
        }else{
            System.out.println(user.getUsername());
            R r=new R();
            r.setData(user.getUsername());
            r.setFlag(true);
            r.setMsg("用户已登录");
            return r;
        }
    }




    /**
     * 修改用户信息
     */
    @PutMapping("/{userId}")
    public boolean updateUser(@RequestBody User user, HttpSession session) {
        // 验证用户是否已登录
        User currentUser = (User) session.getAttribute("user");
        if (currentUser == null) {
            return false;
        }
        // 验证用户是否合法

        // 更新用户信息
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        user.setUpdateTime(date);
        boolean success = userService.updateById(user);
        return success;
    }


    @GetMapping("/logout")
    public R logout(HttpSession session) {
        session.invalidate();
        return new R(true,"登出成功");
    }

}

