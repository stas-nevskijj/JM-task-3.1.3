package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;

    @Autowired
    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void init() {
        Optional<User> user = userDAO.findByUsername("admin");
        if (user.isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            String encode = passwordEncoder.encode("admin");
            admin.setPassword(encode);
            admin.getRoles().add(new Role("ROLE_ADMIN"));
            admin.setAge(12);
            userDAO.save(admin);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<User> index() {
        return userDAO.index();
    }

    @Override
    @Transactional(readOnly = true)
    public User show(int id) {
        return userDAO.show(id);
    }

    @Override
    @Transactional
    public void save(User person) {
        String encode = passwordEncoder.encode(person.getPassword());
        person.setPassword(encode);

        userDAO.save(person);
    }

    @Override
    @Transactional
    public void update(int id, User updatedPerson) {
        userDAO.update(id, updatedPerson);
    }

    @Override
    @Transactional
    public void delete(int id) {
        userDAO.delete(id);
    }
}
