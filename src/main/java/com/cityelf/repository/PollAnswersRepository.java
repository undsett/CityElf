package com.cityelf.repository;

import com.cityelf.model.PollsAnswer;

import org.springframework.data.repository.CrudRepository;

public interface PollAnswersRepository extends CrudRepository<PollsAnswer, Long> {

  void deleteByPollId(long pollId);
}
