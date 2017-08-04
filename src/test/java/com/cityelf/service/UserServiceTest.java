package com.cityelf.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import com.cityelf.exceptions.AccessDeniedException;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.exceptions.UserValidationException;
import com.cityelf.model.Address;
import com.cityelf.model.User;
import com.cityelf.model.UserRole;
import com.cityelf.repository.UserRepository;
import com.cityelf.repository.UserRoleRepository;

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
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

  private User oldUser, newUser, deniedUser;
  private UserRole userRole = new UserRole(1, 1);
  private List<UserRole> userRoles = new ArrayList<>();
  @Autowired
  private UserService userService;
  @MockBean
  private SecurityService securityService;
  @MockBean
  private UserRepository userRepository;
  @MockBean
  private UserRoleRepository userRoleRepository;

  @Before
  public void setUp() {
    oldUser = new User();
    oldUser.setId(1);
    oldUser.setFirebaseId("oldFb");
    oldUser.setEmail("oldEmail");
    oldUser.setPassword("oldPassword");
    oldUser.setPhone("oldPhone");
    oldUser.setAuthorized("not_authorized");
    oldUser.setActivated(true);
    List<Address> addresses = new ArrayList<>();
    Address address1 = new Address();
    address1.setId(4);
    addresses.add(address1);
    Address address2 = new Address();
    address2.setId(3);
    addresses.add(address2);
    oldUser.setAddresses(addresses);

    userRoles.add(userRole);

    newUser = new User();
    newUser.setId(1);
    newUser.setAuthorized("authorized");
    newUser.setPassword("newPassword");
    deniedUser = new User();
    deniedUser.setId(2);
  }

  @Test
  public void updateUserShouldUpdateChangedFields() throws Exception {
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(oldUser));
    when(securityService.getUserFromSession()).thenReturn(oldUser);
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
        assertThat(user.getAddresses().size()).isEqualTo(0);
        assertThat(user.getPhone()).isEqualTo("oldPhone");
        return user;
      }
    });
    userService.updateUser(newUser);
  }

  @Test
  public void updateUserShouldThrowAccessDeniedException() {
    when(securityService.getUserFromSession()).thenReturn(newUser);
    when(userRepository.findById(anyLong())).thenReturn(Optional.of(deniedUser));

    assertThatThrownBy(() -> userService.updateUser(deniedUser))
        .has(new Condition<Throwable>() {
          @Override
          public boolean matches(Throwable value) {
            return value.getClass() == AccessDeniedException.class;
          }
        });
  }

  @Test
  public void updateUserShouldThrowUserNotFoundException() throws Exception {
    when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
    when(securityService.getUserFromSession()).thenReturn(oldUser);
    assertThatThrownBy(() -> userService.updateUser(newUser))
        .has(new Condition<Throwable>() {
          @Override
          public boolean matches(Throwable value) {
            return value.getClass() == UserNotFoundException.class;
          }
        });
  }

  @Test
  public void updateAnonimShouldThrowUserValidationException() {
    when(userRepository.findByFirebaseId(any())).thenReturn(Optional.of(oldUser));

    assertThatThrownBy(() -> userService.updateAnonime(oldUser))
        .has(new Condition<Throwable>() {
          @Override
          public boolean matches(Throwable value) {
            return value.getClass() == UserValidationException.class;
          }
        });
  }

  @Test
  public void updateAnonimShouldThrowUserNotFoundException1() throws Exception {
    when(userRepository.findByFirebaseId(any())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> userService.updateAnonime(oldUser))
        .has(new Condition<Throwable>() {
          @Override
          public boolean matches(Throwable value) {
            return value.getClass() == UserNotFoundException.class;
          }
        });
  }

  @Test
  public void updateAnonimShouldThrowUserValidationException2() throws Exception {
    when(userRepository.findByFirebaseId(any())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> userService.updateAnonime(deniedUser))
        .has(new Condition<Throwable>() {
          @Override
          public boolean matches(Throwable value) {
            return value.getClass() == UserValidationException.class;
          }
        });
  }

  /** todo fix the tests
   @Test public void addUserShouldThrowUserAlreadyExistsException() {
   when(userRepository.findByFirebaseId(any(String.class))).thenReturn(oldUser);

   assertThatThrownBy(() -> userService.addNewUser(oldUser))
   .has(new Condition<Throwable>() {
   @Override public boolean matches(Throwable value) {
   return value.getClass() == UserAlreadyExistsException.class;
   }
   });
   }

   @Test public void addUserShouldThrowUserNoFirebaseIdException() {
   when(userRepository.findByFirebaseId(any(String.class))).thenReturn(oldUser);

   assertThatThrownBy(() -> userService.addNewUser(newUser))
   .has(new Condition<Throwable>() {
   @Override public boolean matches(Throwable value) {
   return value.getClass() == UserNoFirebaseIdException.class;
   }
   });
   }
   **/
}