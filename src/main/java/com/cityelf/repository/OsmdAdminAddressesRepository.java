package com.cityelf.repository;

import com.cityelf.model.OsmdAdminAddresses;

import org.springframework.data.repository.CrudRepository;

public interface OsmdAdminAddressesRepository extends CrudRepository<OsmdAdminAddresses, Long> {

  OsmdAdminAddresses findByUserAdminId(long userAdminId);

  OsmdAdminAddresses findByAddressId(long addressId);
}
