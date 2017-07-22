package com.cityelf.repository;

import com.cityelf.model.Poll;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PollsRepository extends CrudRepository<Poll, Long> {

  List<Poll> findByAddressId(long addressId);

}
