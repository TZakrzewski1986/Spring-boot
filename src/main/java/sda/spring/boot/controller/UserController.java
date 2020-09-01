package sda.spring.boot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sda.spring.boot.exception.UserAlreadyExistsException;
import sda.spring.boot.exception.UserNotFoundException;
import sda.spring.boot.model.User;
import sda.spring.boot.model.UserList;
import sda.spring.boot.service.UserService;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/users/{name}", headers = "X-API-VERSION=1")
    public User getUserByName(@PathVariable String name) throws UserNotFoundException {
        return userService.getUserByName(name);
    }

//    @GetMapping("/users/v2/{id}")
    @GetMapping(value = "/users/{id}", headers = "X-API-VERSION=2")
    public User getUserById(@PathVariable Long id) throws UserNotFoundException {
        return userService.findUserById(id);
    }

    @GetMapping("/users")
    public UserList getAllUsers() {
        return new UserList(userService.getAllUsers());
    }

    @PostMapping("/users")
    public User addNewUser(@RequestBody @Valid User user) throws UserAlreadyExistsException {
        return userService.addNewUser(user);
    }

    @PutMapping("/users/{name}")
    public User updateUser(@RequestBody User user, @PathVariable String name) throws UserNotFoundException {
        return userService.updateUser(user, name);
    }

    @DeleteMapping("/users/{name}")
    public void deleteUser(@PathVariable String name) throws UserNotFoundException {
        userService.deleteUser(name);
    }
}
