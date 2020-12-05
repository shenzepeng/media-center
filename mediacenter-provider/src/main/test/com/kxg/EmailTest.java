package com.kxg;

import kxg.sso.mediacenter.provider.service.impl.MailService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 要写注释呀
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = kxg.sso.mediacenter.provider.DubboProviderBootstrap.class)
public class EmailTest {
    @Autowired
    private MailService mailService;


    @Test
    public void test2(){
        List<String> recivers = mailService.getRecivers();
        mailService.sent(recivers,"测试","sdf");
    }
}
