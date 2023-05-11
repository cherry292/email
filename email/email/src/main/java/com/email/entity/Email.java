package com.email.entity;

import java.util.Date;

import lombok.Data;


@Data
public class Email {
    //邮件id
    private Integer id;
    //发件人
    private String senderEmail;
    //收件人
    private String recipientEmail;
    //抄送人
    private String ccRecipientEmail;
    //密送人
    private String bccRecipientEmail;
    //主题
    private String subject;
    //正文
    private String content;
    //附件id列表
    private int attachmentIds;
    //是否已读
    private boolean isRead;
    //是否草稿
    private boolean isDraft;
    //是否垃圾邮件
    private boolean isSpam;
    //创建时间
    private Date createTime;
    //更新时间
    private Date updateTime;
    private boolean isSend;

    private boolean isRecipient;

    public void setisDraft(boolean bl) {
        this.isDraft=bl;
    }
    public void setisRead(boolean bl) {
        this.isSpam=bl;
    }
    public void setisSpam(boolean bl) {
        this.isSpam=bl;
    }
    public void isRecipient(boolean bl) {
        this.isRecipient=bl;
    }
    public void setisRecipient(boolean bl) {
        this.isRecipient=bl;
    }
    public void setisSend(boolean bl){this.isSend=bl;}
}

