package com.cityelf.utils.address.finder.utils;

import static org.assertj.core.api.Assertions.assertThat;

import com.cityelf.model.Address;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressFilterTest {

  @Autowired
  private AddressFilter addressFilter;

  @Test
  public void filterAddresses() throws Exception {
    Address address = new Address("Лазурный 1-й переулок, 7");
    List<Address> addresses = addressFilter
        .filterAddresses(Arrays.asList(address), Arrays.asList("1", "8"), "Лазурный 1-й пер.");
    assertThat(addresses.size()).isEqualTo(0);
  }

  @Test
  public void filterAddresses2() throws Exception {
    Address address = new Address("Лазурный 1-й переулок, 7");
    List<Address> addresses = addressFilter
        .filterAddresses(Arrays.asList(address), Arrays.asList("2", "7"), "Лазурный 1-й пер.");
    assertThat(addresses.size()).isEqualTo(1);
    assertThat(addresses.get(0).getAddress()).isEqualTo(address.getAddress());
  }

  @Test
  public void filterAddresses3() throws Exception {
    Address address = new Address("Лазурный 1-й переулок, 7/б");
    List<Address> addresses = addressFilter
        .filterAddresses(Arrays.asList(address), Arrays.asList("2", "7"), "Лазурный 1-й пер.");
    assertThat(addresses.size()).isEqualTo(0);
  }

  @Test
  public void filterAddresses4() throws Exception {
    Address address = new Address("Лазурный 1-й переулок, 7/б");
    List<Address> addresses = addressFilter
        .filterAddresses(Arrays.asList(address), Arrays.asList("1", "7-б", "17"),
            "Лазурный 1-й пер.");
    assertThat(addresses.size()).isEqualTo(1);
    assertThat(addresses.get(0).getAddress()).isEqualTo(address.getAddress());
  }
}