package com.cityelf.repository;

import com.cityelf.model.PollsAnswer;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PollAnswersRepository extends CrudRepository<PollsAnswer, Long> {

  List<PollsAnswer> findByPollId(long pollId);

  void deleteByPollId(long pollId);
}
