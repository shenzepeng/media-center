package kxg.sso.mediacenter.provider.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 要写注释呀
 */
@Service
public class MailService {
    @Autowired
    private JavaMailSenderImpl mailSender;

    public void sent(List<String> mails, String title, String content) {
        //简单邮件
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject(title);
        simpleMailMessage.setText(content);
        simpleMailMessage.setFrom("1930352617@qq.com");
        mails.forEach(t -> {
            simpleMailMessage.setTo(t);
            try {
                mailSender.send(simpleMailMessage);
            }catch (Exception e){
               // System.out.println(e);
            }
        });
    }

    public List<String> getRecivers(){
        return Arrays.asList("806871609@qq.com","695158027@qq.com","1661586220@qq.com");
    }
}
