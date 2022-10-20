package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;
import java.util.Optional;

public interface UserDAO {
    List<User> index();
    User show(int id);
    void save(User person);
    void update(int id, User updatedPerson);
    void delete(int id);

    Optional<User> findByUsername(String username);
}
