package com.cityelf.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import com.cityelf.exceptions.UserAlreadyExistsException;
import com.cityelf.exceptions.UserNoFirebaseIdException;
import com.cityelf.exceptions.UserNotAuthorizedException;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.model.Address;
import com.cityelf.model.User;
import com.cityelf.repository.UserRepository;

import org.assertj.core.api.Condition;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

  private User oldUser, newUser;
  @Autowired
  private UserService userService;
  @MockBean
  private UserRepository userRepository;

  @Before
  public void setUp() {
    oldUser = new User();
    oldUser.setId(1);
    oldUser.setFirebaseId("oldFb");
    oldUser.setEmail("oldEmail");
    oldUser.setPassword("oldPassword");
    oldUser.setPhone("oldPhone");
    oldUser.setAuthorized("not_authorized");
    List<Address> addresses = new ArrayList<>();
    Address address1 = new Address();
    address1.setId(4);
    addresses.add(address1);
    Address address2 = new Address();
    address2.setId(3);
    addresses.add(address2);
    oldUser.setAddresses(addresses);

    newUser = new User();
    newUser.setId(1);
    newUser.setAuthorized("authorized");
    newUser.setPassword("newPassword");
  }

  @Test
  public void updateUserShouldUpdateChangedFields() throws Exception {
    when(userRepository.findOne(anyLong())).thenReturn(oldUser);
    when(userRepository.save(any(User.class))).thenAnswer(new Answer<User>() {
      @Override
      public User answer(InvocationOnMock invocation) throws Throwable {
        Object[] arguments = invocation.getArguments();
        assertThat(arguments[0]).isExactlyInstanceOf(User.class);
        User user = (User) arguments[0];
        assertThat(user.getId()).isEqualTo(1);
        assertThat(user.getFirebaseId()).isEqualTo("oldFb");
        assertThat(user.getEmail()).isEqualTo("oldEmail");
        assertThat(user.getPassword()).isEqualTo("newPassword");
        assertThat(user.getAddresses().size()).isEqualTo(2);
        assertThat(user.getPhone()).isEqualTo("oldPhone");
        return user;
      }
    });

    userService.updateUser(newUser);
  }

  @Test
  public void updateUserShouldThrowUserNotFoundException() throws Exception {
    when(userRepository.findOne(anyLong())).thenReturn(null);

    assertThatThrownBy(() -> userService.updateUser(newUser))
        .has(new Condition<Throwable>() {
          @Override
          public boolean matches(Throwable value) {
            return value.getClass() == UserNotFoundException.class;
          }
        });
  }

  @Test
  public void updateUserShouldThrowUserNotAuthorizedException() {
    when(userRepository.findOne(anyLong())).thenReturn(oldUser);

    assertThatThrownBy(() -> userService.updateUser(oldUser))
        .has(new Condition<Throwable>() {
          @Override
          public boolean matches(Throwable value) {
            return value.getClass() == UserNotAuthorizedException.class;
          }
        });
  }

  /** todo fix the tests
  @Test
  public void addUserShouldThrowUserAlreadyExistsException() {
    when(userRepository.findByFirebaseId(any(String.class))).thenReturn(oldUser);

    assertThatThrownBy(() -> userService.addNewUser(oldUser))
        .has(new Condition<Throwable>() {
          @Override
          public boolean matches(Throwable value) {
            return value.getClass() == UserAlreadyExistsException.class;
          }
        });
  }

  @Test
  public void addUserShouldThrowUserNoFirebaseIdException() {
    when(userRepository.findByFirebaseId(any(String.class))).thenReturn(oldUser);

    assertThatThrownBy(() -> userService.addNewUser(newUser))
        .has(new Condition<Throwable>() {
          @Override
          public boolean matches(Throwable value) {
            return value.getClass() == UserNoFirebaseIdException.class;
          }
        });
  }
  **/
}