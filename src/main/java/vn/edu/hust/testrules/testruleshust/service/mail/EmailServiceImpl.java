package vn.edu.hust.testrules.testruleshust.service.mail;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.service.mail.servicerequest.EmailDetails;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    @Override
    public Boolean sendSimpleMail(EmailDetails details) {

        try {


            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);

            helper.setSubject(details.getSubject());
            helper.setFrom(sender);
            helper.setTo(details.getRecipient());
            boolean html = true;
            helper.setText(details.getMsgBody(), html);

//            SimpleMailMessage mailMessage
//                    = new SimpleMailMessage();
//
//            mailMessage.setFrom(sender);
//            mailMessage.setTo(details.getRecipient());
//            mailMessage.setText(details.getMsgBody());
//            mailMessage.setSubject(details.getSubject());

            javaMailSender.send(message);
            return Boolean.TRUE;
        } catch (Exception ex) {
            return Boolean.FALSE;
        }
    }
}
