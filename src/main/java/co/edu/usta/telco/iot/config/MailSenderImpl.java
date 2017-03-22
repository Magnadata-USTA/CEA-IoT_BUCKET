package co.edu.usta.telco.iot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Mail sender class
 */
@Component
public class MailSenderImpl{

    @Autowired
    private JavaMailSender javaMailSender;

    public void send(String mailTo) {
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(mailTo);
            helper.setReplyTo("cjangaritas@gmail.com");
            helper.setFrom("cjangaritas@gmail.com");
            helper.setSubject("IoT repository - Account activation pending");
            helper.setText("Your account is in process of verification");
            javaMailSender.send(mail);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
