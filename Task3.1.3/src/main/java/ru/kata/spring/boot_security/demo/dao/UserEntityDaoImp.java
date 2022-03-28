package ru.kata.spring.boot_security.demo.dao;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.UserEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Repository
public class UserEntityDaoImp implements UserEntityDao {

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public UserEntity getUserEntityByUsername(String username) {
        TypedQuery<UserEntity> query = entityManager.createQuery("SELECT u FROM UserEntity u where u.username = :username", UserEntity.class);
        query.setParameter("username", username);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void add(UserEntity user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entityManager.persist(user);
    }

    @Override
    public void update(UserEntity user, Set<Role> roles) {
        user.setRoles(roles);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        entityManager.merge(user);
    }

    @Override
    public void remove(int id) {
        UserEntity user = entityManager.find(UserEntity.class, id);
        entityManager.remove(user);
    }

    @Override
    public UserEntity getUserById(int id) {
        return entityManager.find(UserEntity.class, id);
    }

    @Override
    public List<UserEntity> getAllUsers() {
        return entityManager.createQuery("select u from UserEntity u", UserEntity.class).getResultList();
    }
}
