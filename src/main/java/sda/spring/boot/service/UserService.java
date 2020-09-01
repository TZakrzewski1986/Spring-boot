package sda.spring.boot.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import sda.spring.boot.exception.UserAlreadyExistsException;
import sda.spring.boot.exception.UserNotFoundException;
import sda.spring.boot.model.User;
import sda.spring.boot.repository.UserRepository;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new UserNotFoundException("Brak usera"));
    }

    public User getUserByName(String name) throws UserNotFoundException {
        return findUserByName(name)
                .orElseThrow(() ->
                        new UserNotFoundException("Brak user'a o imieniu: " + name));
    }

    private Optional<User> findUserByName(String name) {
        return userRepository.findUserByName(name);
    }

    public User addNewUser(User user) throws UserAlreadyExistsException {
        if(findUserByName(user.getName()).isPresent()) {
            throw new UserAlreadyExistsException("Taki user juz istnieje");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(User user, String name) throws UserNotFoundException {
        User userByName = getUserByName(name);

        userByName.setAge(user.getAge());
        userByName.setName(user.getName());
        userByName.setSurname(user.getSurname());

        return userRepository.save(userByName);
    }

    public void deleteUser(String name) throws UserNotFoundException {
        User existingUser = getUserByName(name);
        userRepository.delete(existingUser);
    }
}
