package com.cityelf.service;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailSenderService {

  private Logger logger = LogManager.getLogger(getClass());
  @Autowired
  private JavaMailSender mailSender;
  private ExecutorService executorService = Executors.newSingleThreadExecutor();

  public void sendMail(String sendTo, String mailSubject, String mailContent) {
    try {
      SimpleMailMessage mailMessage = new SimpleMailMessage();
      mailMessage.setTo(sendTo);
      mailMessage.setText(mailContent);
      mailMessage.setSubject(mailSubject);
      mailSender.send(mailMessage);
    } catch (MailException ex) {
      logger.error("Error while sending mail", ex);
      throw ex;
    }
  }

  public void send(String mailTo,
      String mailFrom,
      String subject,
      String mailContent,
      String fileName,
      InputStreamSource inputStreamSource) {
    try {
      MimeMessage message = mailSender.createMimeMessage();

      MimeMessageHelper helper = new MimeMessageHelper(message, true);
      helper.setTo(mailTo);
      helper.setFrom(mailFrom);
      helper.setText(mailContent);
      helper.setSubject(subject);

      helper.addAttachment(fileName, inputStreamSource);

      mailSender.send(message);
    } catch (MessagingException ex) {
      logger.error(ex.getMessage(), ex);
      throw new MailSendException("Error while sending mail", ex);
    }
  }

  public void sendMeAsync(String to, String subject, String message) {
    executorService.submit(new Runnable() {
      @Override
      public void run() {
        sendMail(to, subject, message);
      }
    });
  }
}
