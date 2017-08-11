package com.cityelf.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import com.cityelf.model.Address;
import com.cityelf.repository.AddressesRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTest {

  @Autowired
  private AddressService addressService;
  @MockBean
  private AddressesRepository addressesRepository;
  private List<Address> addresses;

  @Before
  public void setUp() {
    addresses = Arrays.asList(new Address("Добровольського проспект, 1"),
        new Address("Добровольского проспект, 114/1"),
        new Address("Добровольского проспект, 157"));
  }

  @Test
  public void getAddresses() throws Exception {
    when(addressesRepository.findSimilarAddresses(anyString())).thenReturn(addresses);
    List<Address> addressList = addressService.getAddresses("добровольского  проспект",
        Arrays.asList("1", "114-1", "157", "159", "128", "151", "130"));
    assertThat(addressList.size()).isEqualTo(3);
  }

}