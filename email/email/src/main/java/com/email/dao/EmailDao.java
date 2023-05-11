package com.email.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.email.entity.Email;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmailDao extends BaseMapper<Email> {

    Email selectEmailWithAttachments(@Param("id") Long id);
}

