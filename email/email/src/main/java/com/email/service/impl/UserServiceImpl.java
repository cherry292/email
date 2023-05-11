package com.email.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.email.dao.UserDao;
import com.email.entity.User;
import com.email.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2023-04-20 15:43:43
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

