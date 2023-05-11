package com.email.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.email.dao.AttachmentDao;
import com.email.dao.EmailDao;
import com.email.entity.Attachment;
import com.email.entity.Email;
import com.email.entity.User;
import com.email.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * (Email)表服务实现类
 *
 * @author makejava
 * @since 2023-04-20 15:43:44
 */
@Service("emailService")
public class EmailServiceImpl extends ServiceImpl<EmailDao, Email> implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private AttachmentDao attachmentDao;
    @Autowired
    private EmailDao emailDao;

    //草稿箱
    @Override
    public IPage<Email> getDraft(IPage page, User user) {
        QueryWrapper<Email> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender_email", user.getEmail())
                .eq("is_draft", true)
                .eq("is_spam", false);
        return emailDao.selectPage(page, queryWrapper);
    }

    //垃圾箱
    @Override
    public IPage<Email> getSpam(IPage page, User user) {
        QueryWrapper<Email> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recipient_email", user.getEmail())
                .eq("is_draft", false)
                .eq("is_spam", true);
        return emailDao.selectPage(page, queryWrapper);
    }

    //发件箱
    @Override
    public IPage<Email> getSent(IPage page, User user) {
        QueryWrapper<Email> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("sender_email", user.getEmail())
                .eq("is_draft", false)
                .eq("is_send", true)
                .eq("is_spam", false);
        return emailDao.selectPage(page, queryWrapper);
    }

    //收件箱
    @Override
    public IPage<Email> getInbox(IPage page, User user) {
        QueryWrapper<Email> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recipient_email", user.getEmail())
                .eq("is_draft", false)
                .eq("is_recipient", true)
                .eq("is_spam", false);

        return emailDao.selectPage(page,queryWrapper);
    }

    @Override
    public boolean send(Email email, User user) {
        String sendemail = user.getEmail();
        System.out.println(sendemail);
        email.setSenderEmail(sendemail);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        email.setUpdateTime(date);
        email.setCreateTime(date);
        email.setisDraft(false);
        email.setisSpam(false);
        email.setisRead(false);
        email.setisSend(true);
        email.setisRecipient(true);
        System.out.println(email);
        if (this.save(email)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean draft(Email email, User user) {
        String sendemail = user.getEmail();
        email.setSenderEmail(sendemail);
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        email.setUpdateTime(date);
        email.setCreateTime(date);
        email.setisDraft(true);
        email.setisSpam(false);
        email.setisRead(false);
        if (this.save(email)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean sendqq(Email email, User user) {
        try {
            if (send(email, user)) {
                MimeMessage message = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message,true);
                helper.setFrom("1840432689@qq.com");
                helper.setTo(email.getRecipientEmail());
                helper.setSubject(email.getSubject());
                helper.setText(email.getContent(),true);
                if (email.getAttachmentIds()!=0){
                    Attachment attachment = attachmentDao.selectById(email.getAttachmentIds());
                    helper.addAttachment(attachment.getFileName(), new File(attachment.getFilePath()));
                }

                javaMailSender.send(message);
                return true;
            } else {
                return false;
            }
        } catch (MessagingException e) {

            return false;
        }

    }




}

