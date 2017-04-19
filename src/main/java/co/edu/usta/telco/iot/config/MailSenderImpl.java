package co.edu.usta.telco.iot.config;

import co.edu.usta.telco.iot.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
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

    @Value("${mail.repository.mailFrom}")
    private String mailFrom;

    @Value("${mail.repository.replyTo}")
    private String replyTo;

    @Async
    public void send(String mailTo, String subject, String body) throws BusinessException{
        MimeMessage mail = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mail, true);
            helper.setTo(mailTo);
            helper.setReplyTo(replyTo);
            helper.setFrom(mailFrom);
            helper.setSubject(subject);
            helper.setText(body);
            javaMailSender.send(mail);
        } catch (MessagingException | MailSendException e) {
            throw new BusinessException("Error: ", e);
        }
    }
}
