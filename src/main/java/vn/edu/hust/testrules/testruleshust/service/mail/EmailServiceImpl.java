package vn.edu.hust.testrules.testruleshust.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.service.mail.servicerequest.EmailDetails;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    @Override
    public Boolean sendSimpleMail(EmailDetails details) {

        try {
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(mailMessage);
            return Boolean.TRUE;
        } catch (Exception ex) {
            return Boolean.FALSE;
        }
    }
}
