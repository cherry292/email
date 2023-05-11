package com.email.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.email.entity.Email;
import com.email.entity.User;

/**
 * (Email)表服务接口
 *
 * @author makejava
 * @since 2023-04-20 15:43:44
 */
public interface EmailService extends IService<Email> {

    IPage<Email> getDraft(IPage page, User user);
    IPage<Email> getSpam(IPage page,User user);
    IPage<Email> getSent(IPage page,User user);
    IPage<Email> getInbox(IPage page,User user);

    boolean send(Email email, User user);
    boolean draft(Email email, User user);

    boolean sendqq(Email email,User user);

}

