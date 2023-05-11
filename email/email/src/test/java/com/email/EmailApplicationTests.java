package com.email;

import com.email.entity.Email;
import com.email.service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

@SpringBootTest
class EmailApplicationTests {


    private String from = "1840432689@qq.com";
    //接收人
    private String to = "t88458643@163.com";
    //标题
    private String subject = "测试邮件";
    //正文
    private String context = "<img src='https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fbkimg.cdn.bcebos.com%2Fpic%2F8326cffc1e178a82b9018131e84f648da97739124247&refer=http%3A%2F%2Fbkimg.cdn.bcebos.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1645001879&t=f4d8895e53576eacf54605dcc63c6861'/><a href='https://www.itcast.cn'>点开有惊喜</a>";
    @Autowired
    private EmailService emailService;

    private String context1 = "测试邮件正文内容";

    @Autowired
    private JavaMailSender javaMailSender;
    @Test
    void sendqq() {
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setFrom(from+"(小甜甜)");
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(context1);
        javaMailSender.send(simpleMailMessage);


    }


}
