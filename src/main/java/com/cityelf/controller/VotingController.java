package com.cityelf.controller;

import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.PollAnswerNotFoundException;
import com.cityelf.exceptions.PollNotFoundException;
import com.cityelf.exceptions.VotedException;
import com.cityelf.exceptions.VotingUnavailableException;
import com.cityelf.model.Poll;
import com.cityelf.service.PollsService;
import com.cityelf.service.VotingService;
import com.cityelf.utils.voting.utils.VoteParam;
import com.cityelf.utils.voting.utils.VoteResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/voting")
public class VotingController {

  @Autowired
  private VotingService votingService;

  @Autowired
  private PollsService pollsService;

  @RequestMapping(method = RequestMethod.GET)
  public List<Poll> getUserPollsList()
      throws AddressNotPresentException, VotingUnavailableException {
    return votingService.getUsersPolls();
  }

  @RequestMapping(value = "/{pollId}", method = RequestMethod.GET)
  public Poll getPoll(@PathVariable("pollId") long pollId)
      throws VotingUnavailableException, PollNotFoundException, IOException {
    votingService.isVoted(pollId);
    return pollsService.getPollById(pollId);
  }

  @RequestMapping(value = "/voice", method = RequestMethod.POST)
  public boolean vote(@RequestBody VoteParam voteParam)
      throws VotingUnavailableException, PollAnswerNotFoundException,
      PollNotFoundException, IOException {
    if (votingService.isVoted(voteParam.getPollId())) {
      throw new VotedException("Already voted");
    }
    return votingService.giveVoice(voteParam.getPollId(), voteParam.getAnswerId());
  }

  @RequestMapping(value = "/{pollId}/results", method = RequestMethod.GET)
  public List<VoteResult> viewResults(@PathVariable("pollId") long pollId)
      throws VotingUnavailableException, PollAnswerNotFoundException,
      PollNotFoundException, IOException {
    if (!votingService.isVoted(pollId)) {
      throw new VotedException("Need voice before watching results");
    }
    return votingService.viewResults(pollId);
  }
}
