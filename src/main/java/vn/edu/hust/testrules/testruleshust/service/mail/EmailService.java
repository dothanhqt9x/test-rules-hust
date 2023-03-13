package vn.edu.hust.testrules.testruleshust.service.mail;

import org.springframework.stereotype.Service;
import vn.edu.hust.testrules.testruleshust.service.mail.servicerequest.EmailDetails;

@Service
public interface EmailService {

    Boolean sendSimpleMail(EmailDetails details);
}
