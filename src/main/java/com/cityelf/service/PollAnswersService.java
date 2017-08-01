package com.cityelf.service;

import com.cityelf.exceptions.PollAnswerNotFoundException;
import com.cityelf.model.PollsAnswer;
import com.cityelf.repository.PollAnswersRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PollAnswersService {

  @Autowired
  private PollAnswersRepository pollAnswersRepository;

  public PollsAnswer getById(long answerId) throws PollAnswerNotFoundException {
    PollsAnswer answer = pollAnswersRepository.findOne(answerId);
    if (answer == null) {
      throw new PollAnswerNotFoundException();
    }
    return answer;
  }

  public List<PollsAnswer> getByPollId(long pollId) throws PollAnswerNotFoundException {
    List<PollsAnswer> answerList = pollAnswersRepository.findByPollId(pollId);
    if ((answerList == null) || (answerList.isEmpty())) {
      throw new PollAnswerNotFoundException();
    }
    return answerList;
  }

  public void update(PollsAnswer answer) throws PollAnswerNotFoundException {
    if (!pollAnswersRepository.exists(answer.getId())) {
      throw new PollAnswerNotFoundException();
    }
    pollAnswersRepository.save(answer);
  }
}
