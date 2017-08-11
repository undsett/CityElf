package com.cityelf.service;

import com.cityelf.repository.SuburbRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuburbService {

  @Autowired
  private SuburbRepository suburbRepository;

  public List<String> getUniqueLocalities() {
    return suburbRepository.getUniqueLocalities();
  }
}
