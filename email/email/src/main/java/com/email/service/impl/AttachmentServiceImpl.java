package com.email.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.email.dao.AttachmentDao;
import com.email.dao.ContactDao;
import com.email.entity.Attachment;
import com.email.entity.Contact;
import com.email.service.AttachmentService;
import com.email.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AttachmentServiceImpl extends ServiceImpl<AttachmentDao, Attachment> implements AttachmentService {


}

