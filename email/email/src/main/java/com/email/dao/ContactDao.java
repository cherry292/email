package com.email.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.email.entity.Contact;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ContactDao extends BaseMapper<Contact> {

}

