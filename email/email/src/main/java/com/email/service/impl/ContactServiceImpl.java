package com.email.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.email.dao.ContactDao;
import com.email.entity.Contact;
import com.email.service.ContactService;
import org.springframework.stereotype.Service;

/**
 * (Contact)表服务实现类
 *
 * @author makejava
 * @since 2023-04-20 15:43:44
 */
@Service("contactService")
public class ContactServiceImpl extends ServiceImpl<ContactDao, Contact> implements ContactService {

}

