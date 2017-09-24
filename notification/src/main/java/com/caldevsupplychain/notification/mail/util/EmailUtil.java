package com.caldevsupplychain.notification.mail.util;

import com.caldevsupplychain.notification.mail.model.EmailTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class EmailUtil {

    @Autowired
    private JavaMailSender javaMailSender;


    // create mime email message
    public void sendMimeMessage(EmailTemplate emailTemplate) throws MessagingException {

        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setSubject(emailTemplate.getSubject());
        helper.setTo(emailTemplate.getToEmail());
        helper.setFrom(emailTemplate.getFromEmail());
        helper.setText(emailTemplate.getContent(), true);
        javaMailSender.send(message);

    }

    // for generating activation link
    public String generateActivationLink(HttpServletRequest request, String token) {

        String activationLinkButtonTitle = "Activate Account Confirmation Link";
        String requestURL = request.getRequestURL().toString();
        String content = requestURL.substring(0, requestURL.lastIndexOf("/")) + "/activate?token=" + token;
        String activationLink = "<a href=\"" + content + "\">" + activationLinkButtonTitle + "</a>";

        return activationLink;
    }

}
