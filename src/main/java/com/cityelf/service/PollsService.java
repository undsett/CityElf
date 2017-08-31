package com.cityelf.service;

import com.cityelf.exceptions.AccessDeniedException;
import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.PollIncorrectException;
import com.cityelf.exceptions.PollNotFoundException;
import com.cityelf.model.OsmdAdminAddresses;
import com.cityelf.model.Poll;
import com.cityelf.model.PollsAnswer;
import com.cityelf.model.User;
import com.cityelf.repository.AddressesRepository;
import com.cityelf.repository.OsmdAdminAddressesRepository;
import com.cityelf.repository.PollAnswersRepository;
import com.cityelf.repository.PollsRepository;
import com.cityelf.repository.UserRepository;

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

  @Autowired
  private OsmdAdminAddressesRepository osmdAdminAddressesRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private AppServerFirebase appServerFirebase;

  public List<Poll> getPolls(long addressId) throws AddressNotPresentException {
    if (!addressesRepository.exists(addressId)) {
      throw new AddressNotPresentException();
    }
    List<Poll> polls = pollsRepository.findByAddressId(addressId);
    for (Poll poll : polls) {
      poll.setPollsAnswers(pollAnswersRepository.findByPollId(poll.getId()));
    }
    return polls;
  }

  public Poll getPollById(long id) throws PollNotFoundException {
    if (pollsRepository.findOne(id) == null) {
      throw new PollNotFoundException();
    }
    Poll poll = pollsRepository.findOne(id);
    poll.setPollsAnswers(pollAnswersRepository.findByPollId(poll.getId()));
    return poll;
  }

  @Transactional
  public Poll addPoll(Poll poll)
      throws PollIncorrectException, AddressNotPresentException, AccessDeniedException {
    if (!addressesRepository.exists(poll.getAddress().getId())) {
      throw new AddressNotPresentException();
    }
    if (poll.getSubject() == null || poll.getPollsAnswers() == null
        || poll.getDescription() == null) {
      throw new PollIncorrectException();
    }
    if (!accessCheck(poll)) {
      throw new AccessDeniedException();
    }
    if (pollsRepository.exists(poll.getId())) {
      poll.setId(0);
    }
    Poll pollFromDb = pollsRepository.save(poll);
    for (PollsAnswer pollAnswer : poll.getPollsAnswers()) {
      pollAnswer.setPoll(pollFromDb);
    }
    List<User> usersFromAddress = (addressesRepository.findById(poll.getAddress().getId()))
        .getUsers();
    if (usersFromAddress != null) {
      try {
        for (User user : usersFromAddress) {
          appServerFirebase.pushFCMNotification(user.getFirebaseId(), "New poll",
              "По адресу " + addressesRepository.findOne(poll.getAddress().getId()).getAddress()
                  + " новый опрос!");
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
    pollAnswersRepository.save(poll.getPollsAnswers());
    return pollFromDb;
  }

  @Transactional
  public void updatePoll(Poll poll)
      throws PollNotFoundException, AddressNotPresentException, AccessDeniedException {
    if (!pollsRepository.exists(poll.getId())) {
      throw new PollNotFoundException();
    }
    if (!addressesRepository.exists(poll.getAddress().getId())) {
      throw new AddressNotPresentException();
    }
    if (!accessCheck(poll)) {
      throw new AccessDeniedException();
    }

    pollAnswersRepository.deleteByPollId(poll.getId());
    for (PollsAnswer pollAnswer : poll.getPollsAnswers()) {
      pollAnswer.setPoll(poll);
    }
    pollAnswersRepository.save(poll.getPollsAnswers());
    pollsRepository.save(poll);
  }

  public void deletePoll(long id) throws PollNotFoundException, AccessDeniedException {
    if (pollsRepository.exists(id)) {
      if (accessCheck(pollsRepository.findOne(id))) {
        pollsRepository.delete(id);
      } else {
        throw new AccessDeniedException();
      }
    } else {
      throw new PollNotFoundException();
    }
  }

  private boolean accessCheck(Poll poll) {
    User user = securityService.getUserFromSession();
    OsmdAdminAddresses osmdAdminAddresses = osmdAdminAddressesRepository
        .findByUserAdminId(user.getId());

    return (osmdAdminAddresses.getAddressId() == poll.getAddress().getId()
        && user.getId() == osmdAdminAddresses.getUserAdminId());
  }
}