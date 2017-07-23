package com.cityelf.service;

import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.AdvertisementIncorrectException;
import com.cityelf.exceptions.AdvertisementNotFoundException;
import com.cityelf.model.Advertisement;
import com.cityelf.repository.AddressesRepository;
import com.cityelf.repository.AdvertisementRepository;

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
      throws AdvertisementIncorrectException, AddressNotPresentException {
    if (!addressesRepository.exists(advertisement.getAddress().getId())) {
      throw new AddressNotPresentException();
    }
    if (advertisement.getSubject() == null || advertisement.getDescription() == null) {
      throw new AdvertisementIncorrectException();
    } else {
      return advertisementRepository.save(advertisement);
    }
  }

  public Advertisement updateAdvertisement(Advertisement advertisement)
      throws AdvertisementNotFoundException, AddressNotPresentException {
    if (!addressesRepository.exists(advertisement.getAddress().getId())) {
      throw new AddressNotPresentException();
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

  public void deleteAdvertisements(long id) throws AdvertisementNotFoundException {
    Advertisement advertisementFromDb = advertisementRepository.findOne(id);
    if (advertisementFromDb != null) {
      advertisementRepository.delete(id);
    } else {
      throw new AdvertisementNotFoundException();
    }
  }
}