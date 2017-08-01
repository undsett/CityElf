package com.cityelf.repository;

import com.cityelf.model.FeedbackMessage;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface FeedbackMessageRepository extends CrudRepository<FeedbackMessage, Long> {

  List<FeedbackMessage> findAllByCustomer(String customer);

  List<FeedbackMessage> findAllByTheme(String theme);

  List<FeedbackMessage> findAllByMessageDateGreaterThanEqualAndMessageDateLessThanEqual(
      LocalDateTime timeBegin, LocalDateTime timeEnd);

  List<FeedbackMessage> findAllByProcessed(boolean processed);
}
