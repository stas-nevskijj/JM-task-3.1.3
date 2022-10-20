package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;


@Component
public class UserDAOImpl implements UserDAO {


    @PersistenceContext
    private EntityManager entityManager;
    private final UsersRepository usersRepository;
    private final RoleDAO roleDAO;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserDAOImpl(UsersRepository usersRepository, RoleDAO roleDAO) {
        this.usersRepository = usersRepository;
        this.roleDAO = roleDAO;
    }

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override

    public List<User> index() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override

    public User show(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void save(User person) {
        Set<Role> copyOfRoles = new HashSet<>(person.getRoles());
        person.getRoles().clear();
        for (Role personRole : copyOfRoles) {
            personRole.setName("ROLE_" + personRole.getName());

            for (Role mainRole : roleDAO.getAllRoles()) {
                if (Objects.equals(mainRole.getName(), personRole.getName())) {
                    person.getRoles().add(mainRole);
                }
            }
        }

        entityManager.persist(person);
    }

    @Override
    public void update(int id, User updatedPerson) {
        User user = entityManager.find(User.class, id);
        user.setAge(updatedPerson.getAge());
        user.setUsername(updatedPerson.getUsername());

        if (!updatedPerson.getPassword().equals("")) {
            String encode = passwordEncoder.encode(updatedPerson.getPassword());
            user.setPassword(encode);
        }

        user.getRoles().clear();
        for (Role userRole : updatedPerson.getRoles()) {
            userRole.setName("ROLE_" + userRole.getName());

            for (Role mainRole : roleDAO.getAllRoles()) {
                if (Objects.equals(mainRole.getName(), userRole.getName())) {
                    user.getRoles().add(mainRole);
                }
            }
        }


    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

}
