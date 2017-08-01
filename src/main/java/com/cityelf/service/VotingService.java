package com.cityelf.service;

import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.PollAnswerNotFoundException;
import com.cityelf.exceptions.PollNotFoundException;
import com.cityelf.exceptions.VotingUnavailableException;
import com.cityelf.model.Address;
import com.cityelf.model.Poll;
import com.cityelf.model.PollsAnswer;
import com.cityelf.model.User;
import com.cityelf.utils.voting.utils.VoteResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VotingService {

  @Autowired
  private UserService userService;

  @Autowired
  private PollsService pollsService;

  @Autowired
  private PollAnswersService pollAnswersService;

  @Autowired
  private VotedVoiceService votedVoiceService;

  public List<Poll> getUsersPolls() throws VotingUnavailableException, AddressNotPresentException {
    List<Poll> pollList = new ArrayList<>();
    for (Address address : this.getUser(this.getSecurityUserName()).getAddresses()) {
      pollList.addAll(pollsService.getPolls(address.getId()));
    }
    return pollList;
  }

  public boolean isVoted(long pollId) throws VotingUnavailableException, PollNotFoundException {
    return votedVoiceService.isUserVoted(pollId, this.userChecking(pollId));
  }

  public boolean giveVoice(long pollId, long pollAnswerId)
      throws VotingUnavailableException, PollNotFoundException, PollAnswerNotFoundException {
    PollsAnswer answer = pollAnswersService.getById(pollAnswerId);
    if (pollId != answer.getPoll().getId()) {
      throw new VotingUnavailableException("Incorrect answer for the poll");
    }
    return
        (votedVoiceService.makeVoice(answer, this.userChecking(answer.getPoll().getId())) != null);
  }

  public List<VoteResult> viewResults(long pollId)
      throws VotingUnavailableException, PollNotFoundException, PollAnswerNotFoundException {
    this.userChecking(pollId);
    List<PollsAnswer> answerList = pollAnswersService.getByPollId(pollId);
    answerList.sort((first, second) -> second.getVoted() - first.getVoted());
    double total = answerList.stream().mapToInt(PollsAnswer::getVoted).sum();
    return answerList.stream().map(obj -> new VoteResult(obj, total)).collect(Collectors.toList());
  }

  private User userChecking(long pollId) throws VotingUnavailableException, PollNotFoundException {
    User user = this.getUser(this.getSecurityUserName());
    this.checkForAddress(user, pollId);
    return user;
  }

  private String getSecurityUserName() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  private User getUser(String userName) throws VotingUnavailableException {
    User user;
    if (userName.equals("anonymousUser")) {
      throw new VotingUnavailableException("User is not logged in");
    }
    try {
      user = userService.getUser(userName);
    } catch (Exception exc) {
      throw new VotingUnavailableException(exc.getMessage(), exc.getCause());
    }
    return user;
  }


  private void checkForAddress(User user, long pollId)
      throws PollNotFoundException, VotingUnavailableException {
    Poll poll = this.getPoll(pollId);
    if (poll == null) {
      throw new PollNotFoundException();
    }
    if (!user.getAddresses().contains(poll.getAddress())) {
      throw new VotingUnavailableException("Poll doesn't belong to user addresses");
    }
  }

  private Poll getPoll(long pollId) throws PollNotFoundException {
    return pollsService.getPollById(pollId);
  }
}
