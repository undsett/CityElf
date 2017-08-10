package com.cityelf.repository;

import com.cityelf.model.VotedVoice;

import org.springframework.data.repository.CrudRepository;

public interface VotedVoiceRepository extends CrudRepository<VotedVoice, Long> {
  VotedVoice findByPollIdAndUserId(long pollId, long userId);
}
