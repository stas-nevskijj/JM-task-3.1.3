package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService {
    private final UserDAO userDAO;
    private UsersRepository usersRepository;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UsersRepository usersRepository) {
        this.userDAO = userDAO;
        this.usersRepository = usersRepository;
    }

    private PasswordEncoder passwordEncoder;
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void init() {
        Optional<User> user = usersRepository.findByUsername("admin");
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
    public List<User> index() {
        return userDAO.index();
    }

    @Override
    public User show(int id) {
        return userDAO.show(id);
    }

    @Override
    public void save(User person) {
        String encode = passwordEncoder.encode(person.getPassword());
        person.setPassword(encode);

        person.getRoles().add(new Role("ROLE_USER"));
        userDAO.save(person);
    }

    @Override
    public void update(int id, User updatedPerson) {
        userDAO.update(id, updatedPerson);
    }

    @Override
    public void delete(int id) {
        userDAO.delete(id);
    }
}
