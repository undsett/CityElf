package com.cityelf.repository;

import com.cityelf.model.ShutdownReport;
import com.cityelf.model.User;
import com.cityelf.model.UserReports;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserReportsRepository extends CrudRepository<UserReports, Long> {

  List<UserReports> findByShutdownReport(long id);

  List<UserReports> findByShutdownReport(ShutdownReport shutdownReport);

  void deleteAllByShutdownReport(ShutdownReport shutdownReport);
}
