package com.cityelf.repository;

import com.cityelf.model.Poll;

import org.springframework.data.repository.CrudRepository;

public interface PollsRepository extends CrudRepository<Poll, Long> {

}
