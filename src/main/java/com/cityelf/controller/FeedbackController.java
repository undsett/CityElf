package com.cityelf.controller;

import com.cityelf.exceptions.FeedbackMessageException;
import com.cityelf.model.FeedbackMessage;
import com.cityelf.service.FeedbackMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/services/feedback")
public class FeedbackController {

  @Autowired
  private FeedbackMessageService feedbackMessageService;

  @RequestMapping(value = "/sendmail", method = RequestMethod.POST)
  private void sendEmail(@RequestBody FeedbackMessage message) throws FeedbackMessageException {
    feedbackMessageService.sendEmail(message);
  }

  @RequestMapping(value = "/sendmessage", method = RequestMethod.POST)
  private void sendToBase(@RequestBody FeedbackMessage message) throws FeedbackMessageException {
    feedbackMessageService.sendToBase(message);
  }

  @RequestMapping(value = "/get/bydate", method = RequestMethod.GET)
  private List<FeedbackMessage> getByDate(@RequestParam(name = "date") String date) {
    return feedbackMessageService.getAllByDate(LocalDate.parse(date));
  }

  @RequestMapping(value = "/get/byprocessed", method = RequestMethod.GET)
  private List<FeedbackMessage> getByProcessed(@RequestParam(name = "proc") boolean processed) {
    return feedbackMessageService.getAllByProcessed(processed);
  }
}
