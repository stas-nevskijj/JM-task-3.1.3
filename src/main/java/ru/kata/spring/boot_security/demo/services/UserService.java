package ru.kata.spring.boot_security.demo.services;


import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {
    List<User> index();
    User show(int id);
    void save(User person);
    void update(int id, User updatedPerson);
    void delete(int id);

}
