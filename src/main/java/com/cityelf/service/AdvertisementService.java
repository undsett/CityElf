package com.cityelf.service;

import com.cityelf.exceptions.AccessDeniedException;
import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.AdvertisementIncorrectException;
import com.cityelf.exceptions.AdvertisementNotFoundException;
import com.cityelf.model.Advertisement;
import com.cityelf.model.OsmdAdminAddresses;
import com.cityelf.model.User;
import com.cityelf.repository.AddressesRepository;
import com.cityelf.repository.AdvertisementRepository;
import com.cityelf.repository.OsmdAdminAddressesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.List;

@Service
public class AdvertisementService {

  @Autowired
  private AdvertisementRepository advertisementRepository;

  @Autowired
  private AddressesRepository addressesRepository;

  @Autowired
  private OsmdAdminAddressesRepository osmdAdminAddressesRepository;

  @Autowired
  private SecurityService securityService;

  @Autowired
  private AppServerFirebase appServerFirebase;

  public List<Advertisement> getAdvertisements(long addressId) throws AddressNotPresentException {
    if (!addressesRepository.exists(addressId)) {
      throw new AddressNotPresentException();
    }
    return advertisementRepository.findByAddressId(addressId);
  }

  public Advertisement getAdvertisementById(long id) throws AdvertisementNotFoundException {
    if (advertisementRepository.findOne(id) == null) {
      throw new AdvertisementNotFoundException();
    }
    return advertisementRepository.findOne(id);
  }

  public Advertisement addAdvertisement(Advertisement advertisement)
      throws AdvertisementIncorrectException, AddressNotPresentException, AccessDeniedException {
    if (!addressesRepository.exists(advertisement.getAddress().getId())) {
      throw new AddressNotPresentException();
    }
    if (advertisement.getSubject() == null || advertisement.getDescription() == null) {
      throw new AdvertisementIncorrectException();
    }
    if (!accessCheck(advertisement)) {
      throw new AccessDeniedException();
    }
    if (advertisementRepository.exists(advertisement.getId())) {
      advertisement.setId(0);
    }
    List<User> usersFromAddress = (addressesRepository.findById(advertisement.getAddress().getId()))
        .getUsers();
    if (usersFromAddress != null) {
      try {
        for (User user : usersFromAddress) {
          appServerFirebase.pushFCMNotification(user.getFirebaseId(), "New advertisement",
              "По адресу " + addressesRepository.findById(advertisement.getAddress().getId()).getAddress()
                  + " новое объявление!");
        }
      } catch (Exception exception) {
        exception.printStackTrace();
      }
    }
    return advertisementRepository.save(advertisement);
  }

  public Advertisement updateAdvertisement(Advertisement advertisement)
      throws AdvertisementNotFoundException, AddressNotPresentException, AccessDeniedException {
    if (!addressesRepository.exists(advertisement.getAddress().getId())) {
      throw new AddressNotPresentException();
    }
    if (!accessCheck(advertisement)) {
      throw new AccessDeniedException();
    }

    Advertisement advertisementFromDb = advertisementRepository.findOne(advertisement.getId());
    if (advertisementFromDb != null) {
      Field[] fields = advertisement.getClass().getDeclaredFields();
      AccessibleObject.setAccessible(fields, true);
      for (Field field : fields) {
        if (ReflectionUtils.getField(field, advertisement) == null) {
          Object fieldFromDb = ReflectionUtils.getField(field, advertisementFromDb);
          ReflectionUtils.setField(field, advertisement, fieldFromDb);
        }
      }
      return advertisementRepository.save(advertisement);
    } else {
      throw new AdvertisementNotFoundException();
    }
  }

  public void deleteAdvertisement(long id)
      throws AdvertisementNotFoundException, AccessDeniedException {
    if (addressesRepository.exists(id)) {
      if (accessCheck(advertisementRepository.findOne(id))) {
        advertisementRepository.delete(id);
      } else {
        throw new AccessDeniedException();
      }
    } else {
      throw new AdvertisementNotFoundException();
    }
  }

  private boolean accessCheck(Advertisement advertisement) {
    User user = securityService.getUserFromSession();
    OsmdAdminAddresses osmdAdminAddresses = osmdAdminAddressesRepository
        .findByUserAdminId(user.getId());

    return (osmdAdminAddresses.getAddressId() == advertisement.getAddress().getId()
        && user.getId() == osmdAdminAddresses.getUserAdminId());
  }
}