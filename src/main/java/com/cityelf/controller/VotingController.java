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

import java.util.List;

@RestController
@RequestMapping("/services/voting")
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

  @RequestMapping(value = "/{pollid}", method = RequestMethod.GET)
  public Poll getPoll(@PathVariable("pollid") long pollid)
      throws VotingUnavailableException, PollNotFoundException {
    votingService.isVoted(pollid);
    return pollsService.getPollById(pollid);
  }

  @RequestMapping(value = "/voice", method = RequestMethod.POST)
  public boolean vote(@RequestBody VoteParam voteParam)
      throws VotingUnavailableException, PollAnswerNotFoundException, PollNotFoundException {
    if (votingService.isVoted(voteParam.getPollId())) {
      throw new VotedException("Already voted");
    }
    return votingService.giveVoice(voteParam.getPollId(), voteParam.getAnswerId());
  }

  @RequestMapping(value = "/{pollid}/results", method = RequestMethod.GET)
  public List<VoteResult> viewResults(@PathVariable("pollid") long pollid)
      throws VotingUnavailableException, PollAnswerNotFoundException, PollNotFoundException {
    if (!votingService.isVoted(pollid)) {
      throw new VotedException("Need voice before watching results");
    }
    return votingService.viewResults(pollid);
  }
}
