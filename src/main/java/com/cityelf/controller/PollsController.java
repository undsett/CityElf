package com.cityelf.controller;

import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.PollIncorrectException;
import com.cityelf.exceptions.PollNotFoundException;
import com.cityelf.model.Poll;
import com.cityelf.service.PollsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/polls")
public class PollsController {

  @Autowired
  private PollsService pollsService;

  @RequestMapping(value = "/getAll", method = RequestMethod.GET)
  public List<Poll> getPolls(@RequestParam(name = "addressid") long addressId)
      throws AddressNotPresentException {
    return pollsService.getPolls(addressId);
  }

  @RequestMapping(value = "/getPoll", method = RequestMethod.GET)
  public Poll getPollById(@RequestParam("id") long id)
      throws PollNotFoundException {
    return pollsService.getPollById(id);
  }

  @RequestMapping(value = "/admin/addPoll", method = RequestMethod.POST)
  public Poll addPoll(@RequestBody Poll poll)
      throws PollIncorrectException, AddressNotPresentException {
    return pollsService.addPoll(poll);
  }

  @RequestMapping(value = "/admin/updatePoll", method = RequestMethod.PUT)
  public void updatePoll(@RequestBody Poll poll)
      throws PollNotFoundException, AddressNotPresentException {
    pollsService.updatePoll(poll);
  }

  @RequestMapping(value = "/admin/deletePoll", method = RequestMethod.DELETE)
  public void deletePoll(@RequestParam("id") long id)
      throws PollNotFoundException {
    pollsService.deletePoll(id);
  }
}
