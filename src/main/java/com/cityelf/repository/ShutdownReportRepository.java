package com.cityelf.repository;

import com.cityelf.model.Address;
import com.cityelf.model.ShutdownReport;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ShutdownReportRepository extends CrudRepository<ShutdownReport, Long> {

  Optional<ShutdownReport> findByAddress(Address address);

  Optional<ShutdownReport> findByAddress_Address(String address);
}
