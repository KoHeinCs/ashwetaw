package com.ashwetaw.email;

import com.ashwetaw.entities.Student;
import com.ashwetaw.services.StudentService;
import com.ashwetaw.util.ExcelUtil;
import lombok.RequiredArgsConstructor;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.email.EmailPopulatingBuilder;
import org.simplejavamail.api.email.Recipient;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import javax.activation.DataSource;
import javax.mail.internet.MimeMessage;
import javax.mail.util.ByteArrayDataSource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import static com.ashwetaw.email.EmailConstant.*;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailSessionService emailSessionService;
    private final StudentService studentService;

    public void sendStudentsEmail(String email){
        Mailer mailer = MailerBuilder.usingSession(emailSessionService.getEmailSession()).buildMailer();
        mailer.sendMail(buildEmailFormWithExcelAttachment(email));
    }

    public void sendNewPasswordEmail(String userName,String email,String newPassword){
        Mailer mailer = MailerBuilder.usingSession(emailSessionService.getEmailSession()).buildMailer();
        mailer.sendMail(buildEmailForm(userName,email,newPassword));
    }

    private Email buildEmailForm(String userName,String email, String newPassword) {
        Recipient recipient = new Recipient(email,email, MimeMessage.RecipientType.TO);
        EmailPopulatingBuilder emailBuilder = EmailBuilder
                .startingBlank()
                .to(recipient)
                .from(FROM_EMAIL)
                .withSubject(EMAIL_SUBJECT)
                .withPlainText("Dear "+userName+", \n \n Your new account password is: "+newPassword+"\n \n The Support Team")
                .fixingSentDate(new Date());
        return emailBuilder.buildEmail();
    }

    private Email buildEmailFormWithExcelAttachment(String email) {
        Recipient recipient = new Recipient(email,email, MimeMessage.RecipientType.TO);
        DataSource dataSource = new ByteArrayDataSource(getStudentExcelAsByteArray().toByteArray(), "application/vnd.ms-excel");
        EmailPopulatingBuilder emailBuilder = EmailBuilder
                .startingBlank()
                .to(recipient)
                .from(FROM_EMAIL)
                .withSubject(EMAIL_SUBJECT)
                .withAttachment("students.xls",dataSource)
                .fixingSentDate(new Date());
        return emailBuilder.buildEmail();
    }

    public ByteArrayOutputStream getStudentExcelAsByteArray() {
        List<Student> studentList = studentService.getAllStudents();
        return ExcelUtil.studentsToExcel(studentList);
    }
}
