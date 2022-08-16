package com.ashwetaw.email;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jndi.JndiTemplate;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.naming.NamingException;
import java.util.Properties;

import static com.ashwetaw.email.EmailConstant.*;


@Service
@Slf4j
public class EmailSessionService {
    private static final String smtpJndi = "java:jboss/mail/OBCASMTP";
    @Value("${spring.profiles-active}")
    private String activeProfile;
    private boolean isUseGmailServer = true;
    public Session getEmailSession(){
        JndiTemplate jndiTemplate = new JndiTemplate();
        Session session = null;
        try {
            if (activeProfile.contains("dev")){
                if (isUseGmailServer){
                    log.info("Email server is used with Gmail");
                    Properties props = new Properties();
                    props.put("mail.transport.protocol", SIMPLE_MAIL_TRANSFER_PROTOCOL);
                    props.put(SMTP_AUTH,true);
                    props.put(SMTP_STARTTLS_ENABLE,true);
                    props.put(SMTP_STARTTLS_REQUIRED,true);
                    props.put(SMTP_HOST,GMAIL_SMTP_SERVER);
                    props.put(SMTP_PORT,DEFAULT_PORT);

                    //debugging purpose only
                    props.put("mail.debug", true);

                    return Session.getInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(USERNAME,PASSWORD);
                        }
                    });

                }else {
                    log.info("Email server is used with internal");
                    final String username = "april@sit.startinpoint.com";
                    final String password = "P@ger123";
                    String host = "192.168.3.108";

                    Properties props = new Properties();
                    props.put(SMTP_AUTH,true);
                    props.put(SMTP_STARTTLS_ENABLE,true);
                    props.put(SMTP_STARTTLS_REQUIRED,true);
                    props.put(SMTP_HOST,host);
                    props.put(SMTP_PORT,25);

                    return Session.getInstance(props, new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username,password);
                        }
                    });

                }
            }else {
                session =(Session) jndiTemplate.lookup(smtpJndi);
            }
        }catch (NamingException exception){
            log.error("NamingException occurred !",exception.getMessage());
        }
        return session;
    }

}
