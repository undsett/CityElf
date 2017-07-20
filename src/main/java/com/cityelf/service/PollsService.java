package com.cityelf.service;

import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.PollIncorrectException;
import com.cityelf.exceptions.PollNotFoundException;
import com.cityelf.model.Poll;
import com.cityelf.model.PollsAnswer;
import com.cityelf.repository.AddressesRepository;
import com.cityelf.repository.PollAnswersRepository;
import com.cityelf.repository.PollsRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

@Service
public class PollsService {

  @Autowired
  private PollsRepository pollsRepository;

  @Autowired
  private AddressesRepository addressesRepository;

  @Autowired
  private PollAnswersRepository pollAnswersRepository;

  public List<Poll> getPolls(long addressId) throws AddressNotPresentException {
    if (!addressesRepository.exists(addressId)) {
      throw new AddressNotPresentException();
    }
    return pollsRepository.findByAddressId(addressId);
  }

  public Poll getPollById(long id) throws PollNotFoundException {
    if (pollsRepository.findOne(id) == null) {
      throw new PollNotFoundException();
    }
    return pollsRepository.findOne(id);
  }

  @Transactional
  public Poll addPoll(Poll poll)
      throws PollIncorrectException, AddressNotPresentException {
    if (!addressesRepository.exists(poll.getAddress().getId())) {
      throw new AddressNotPresentException();
    }
    if (poll.getSubject() == null || poll.getPollsAnswers() == null
        || poll.getDescription() == null) {
      throw new PollIncorrectException();
    }

    Poll pollFromDb = pollsRepository.save(poll);
    for (PollsAnswer pollAnswer : poll.getPollsAnswers()) {
      pollAnswer.setPoll(pollFromDb);
    }
    pollAnswersRepository.save(poll.getPollsAnswers());
    return pollFromDb;
  }

  @Transactional
  public void updatePoll(Poll poll)
      throws PollNotFoundException, AddressNotPresentException {
    if (!pollsRepository.exists(poll.getId())) {
      throw new PollNotFoundException();
    }
    if (!addressesRepository.exists(poll.getAddress().getId())) {
      throw new AddressNotPresentException();
    }
    pollAnswersRepository.deleteByPollId(poll.getId());
    for (PollsAnswer pollAnswer : poll.getPollsAnswers()) {
      pollAnswer.setPoll(poll);
    }
    pollAnswersRepository.save(poll.getPollsAnswers());
    pollsRepository.save(poll);
  }

  public void deletePoll(long id) throws PollNotFoundException {
    if (pollsRepository.exists(id)) {
      pollsRepository.delete(id);
    } else {
      throw new PollNotFoundException();
    }
  }
}
