package sda.spring.boot.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import sda.spring.boot.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByName(@Param("name") String name);

}
