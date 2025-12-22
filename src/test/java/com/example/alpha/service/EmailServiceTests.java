package com.example.alpha.service;

import com.example.alpha.Service.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    public void testSendMail(){
        emailService.sendEmail("niteesh@gmail.com",
                "Testing java mail sender",
                "Hi, app kaise hain");
    }
}
