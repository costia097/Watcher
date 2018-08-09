package net.watcher.domain.services.core;

import net.watcher.domain.services.core.intefaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Email service functionality
 *
 * @author Kostia
 */
@Profile("!qa")
@Service
public class EmailServiceImp implements EmailService {
    @Autowired
    @Qualifier("javaMailSenderImpl")
    private JavaMailSenderImpl javaMailSender;

    public void sendEmailToPerson(String to, String subject, String text) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
            mimeMessageHelper.setFrom("From");
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(text);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
    }
}
