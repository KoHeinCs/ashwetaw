package com.ashwetaw.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

import static com.ashwetaw.email.EmailConstant.*;


@Service
@Slf4j
public class EmailSessionService {

    private boolean isUseGmailServer = true;

    public Session getEmailSession() {
        Session session = null;
        if (isUseGmailServer) {
            log.info("Email server is used with Gmail");
            Properties props = new Properties();
            props.put("mail.transport.protocol", SIMPLE_MAIL_TRANSFER_PROTOCOL);
            props.put(SMTP_AUTH, true);
            props.put(SMTP_STARTTLS_ENABLE, true);
            props.put(SMTP_STARTTLS_REQUIRED, true);
            props.put(SMTP_HOST, GMAIL_SMTP_SERVER);
            props.put(SMTP_PORT, DEFAULT_PORT);

            //debugging purpose only
            props.put("mail.debug", true);

            session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });

        }

        return session;
    }

}
