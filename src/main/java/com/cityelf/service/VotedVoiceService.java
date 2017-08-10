package com.cityelf.service;

import com.cityelf.exceptions.PollAnswerNotFoundException;
import com.cityelf.model.PollsAnswer;
import com.cityelf.model.User;
import com.cityelf.model.VotedVoice;
import com.cityelf.repository.VotedVoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VotedVoiceService {

  @Autowired
  private VotedVoiceRepository voiceRepository;

  @Autowired
  private PollAnswersService pollAnswersService;

  public boolean isUserVoted(long pollId, User user) {
    return (voiceRepository.findByPollIdAndUserId(pollId, user.getId()) != null);
  }

  @Transactional
  public VotedVoice makeVoice(PollsAnswer answer, User user)
      throws PollAnswerNotFoundException {
    if (isUserVoted(answer.getPoll().getId(), user)) {
      return null;
    }
    VotedVoice result = voiceRepository.save(new VotedVoice(user, answer));
    answer.setVoted(answer.getVoted() + 1);
    pollAnswersService.update(answer);
    return result;
  }
}
