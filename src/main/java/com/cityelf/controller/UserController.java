package com.cityelf.controller;

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
@RequestMapping("/users")
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

  @RequestMapping(value = "/upload", method = RequestMethod.POST)
  public void upload(@RequestParam("file") MultipartFile file,
      @RequestParam("mailTo") String mailFrom,
      @RequestParam("address") String address,
      @RequestParam("firstName") String firstName,
      @RequestParam("lastName") String lastName) throws IOException {
    if (!file.isEmpty()) {
      storageService.saveUploaded(file.getInputStream(), file.getOriginalFilename());
      mailSenderService.send(
          applicationEmail,
          mailFrom,
          requestSubject,
          MessageFormat.format(requestTemplate, lastName, firstName, mailFrom, address),
          file.getOriginalFilename(),
          () -> file.getInputStream());
    }
  }

  @RequestMapping("/all")
  public List<User> getAll() {
    return userService.getAll();
  }

  @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
  public User getUser(@PathVariable("userId") long id) throws UserNotFoundException {
    return userService.getUser(id);
  }

  @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
  public void updateUser(@RequestBody @Valid User user, BindingResult bindingResult)
      throws UserException {
    if (bindingResult.hasErrors()) {
      String errorMessage = bindingResult.getFieldErrors()
          .stream()
          .map(error -> error.getDefaultMessage())
          .collect(Collectors.joining(", "));
      throw new UserValidationException(errorMessage);
    }
    userService.updateUser(user);
  }


  @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
  public void deleteUser(@PathVariable("userId") long id) throws UserNotFoundException {
    userService.deleteUser(id);
  }
}