package com.email.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.email.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao extends BaseMapper<User> {

}

