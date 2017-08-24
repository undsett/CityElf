package com.cityelf.controller;

import com.cityelf.exceptions.AccessDeniedException;
import com.cityelf.exceptions.AddressException;
import com.cityelf.exceptions.AddressNotPresentException;
import com.cityelf.exceptions.UserException;
import com.cityelf.exceptions.UserNotFoundException;
import com.cityelf.exceptions.UserValidationException;
import com.cityelf.model.User;
import com.cityelf.service.MailSenderService;
import com.cityelf.service.StorageService;
import com.cityelf.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

@RestController
@RequestMapping("/services/users")
public class UserController {

  @Autowired
  private UserService userService;
  @Autowired
  private MailSenderService mailSenderService;
  @Autowired
  private StorageService storageService;
  @Value("${spring.mail.username}")
  private String applicationEmail;
  @Value("${mail.admin.request.subject}")
  private String requestSubject;
  @Value("${mail.admin.request.template}")
  private String requestTemplate;
  @Value("${mail.user.address.nonexistent.subject}")
  private String nonexistentAddressSubject;
  @Value("${mail.user.address.nonexistent.content.template}")
  private String nonexistentAddressContentTemplate;

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public void upload(@RequestParam("file") MultipartFile file,
      @RequestParam("mailFrom") String mailFrom,
      @RequestParam("address") String address,
      @RequestParam("name") String name) throws IOException {
    if (!file.isEmpty()) {
      storageService.saveUploaded(file.getInputStream(), file.getOriginalFilename());
      mailSenderService.send(
          applicationEmail,
          mailFrom,
          requestSubject,
          MessageFormat.format(requestTemplate, name, mailFrom, address),
          file.getOriginalFilename(),
          () -> file.getInputStream());
    }
  }

  /*@RequestMapping("/all")
  public List<User> getAll() {
    return userService.getAll();
  }*/

  @RequestMapping(value = "/{userid}", method = RequestMethod.GET)
  public User getUser(@PathVariable(name = "userid") long id) throws UserException {
    return userService.getUser(id);
  }

  @RequestMapping(value = "/updateanonim", method = RequestMethod.PUT)
  public void updateAnonim(@RequestBody User user)
      throws UserException, AddressException {
    userService.updateAnonime(user);
  }


  @RequestMapping(value = "/updateuser", method = RequestMethod.PUT)
  public User updateUser(@RequestBody @Valid User user, BindingResult bindingResult)
      throws UserException, AddressException, AccessDeniedException {
    if (bindingResult.hasErrors()) {
      String errorMessage = bindingResult.getFieldErrors()
          .stream()
          .map(error -> error.getDefaultMessage())
          .collect(Collectors.joining(", "));
      throw new UserValidationException(errorMessage);
    }
    try {
      return userService.updateUser(user);
    } catch (AddressNotPresentException ex) {
      mailSenderService.sendMeAsync(
          applicationEmail,
          nonexistentAddressSubject,
          MessageFormat.format(nonexistentAddressContentTemplate, user.getId(), user.getEmail()));
      throw ex;
    }
  }

  @RequestMapping(value = "/{userid}", method = RequestMethod.DELETE)
  public void deleteUser(@PathVariable(name = "userid") long id)
      throws UserException, AccessDeniedException {
    userService.deleteUser(id);
  }

  @RequestMapping(value = "/unionrecords", method = RequestMethod.POST)
  public void unionRecords(@RequestParam(name = "firebaseid") String fireBaseID)
      throws UserNotFoundException {
    userService.unionRecords(fireBaseID);
  }
}
