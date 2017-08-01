package com.cityelf.service;

import com.cityelf.exceptions.FeedbackMessageException;
import com.cityelf.model.FeedbackMessage;
import com.cityelf.repository.FeedbackMessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class FeedbackMessageService {

  @Autowired
  private FeedbackMessageRepository feedbackMessageRepository;

  @Autowired
  private MailSenderService mailSenderService;

  @Value("${spring.mail.username}")
  private String cityElfEmail;

  public void sendEmail(FeedbackMessage message) throws FeedbackMessageException {
    mailSenderService.sendMail(cityElfEmail,
        "Feedback: " + message.getTheme(),
        message.createContent());
  }

  public void sendToBase(FeedbackMessage message) throws FeedbackMessageException {
    message.checkDataLength();
    feedbackMessageRepository.save(message);
  }

  public Iterable<FeedbackMessage> getAllMessages() {
    return feedbackMessageRepository.findAll();
  }

  public List<FeedbackMessage> getAllByCustomer(String customer) {
    return feedbackMessageRepository.findAllByCustomer(customer);
  }

  public List<FeedbackMessage> getAllByDate(LocalDate date) {
    LocalDateTime begin = LocalDateTime.of(date, LocalTime.of(0,0,1));
    LocalDateTime end = LocalDateTime.of(date, LocalTime.of(23,59,59));
    return
        feedbackMessageRepository.findAllByMessageDateGreaterThanEqualAndMessageDateLessThanEqual(
            begin, end);
  }

  public List<FeedbackMessage> getAllByProcessed(boolean processed) {
    return feedbackMessageRepository.findAllByProcessed(processed);
  }
}
