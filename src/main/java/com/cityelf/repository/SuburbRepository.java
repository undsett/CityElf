package com.cityelf.repository;

import com.cityelf.model.Suburb;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SuburbRepository extends CrudRepository<Suburb, Long> {

  @Query(value = "select distinct locality from suburbs",
      nativeQuery = true)
  List<String> getUniqueLocalities();
}
