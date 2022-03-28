package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.UserEntity;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserEntityService;

import javax.persistence.EntityManager;
import java.util.HashSet;

@Component
public class MyDBUtil implements InitializingBean {
    private final UserEntityService userEntityService;
    private final RoleService roleService;

    @Autowired
    public MyDBUtil(UserEntityService userEntityService, RoleService roleService) {
        this.userEntityService = userEntityService;
        this.roleService = roleService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
        roleService.add(new Role("ROLE_ADMIN"));
        roleService.add(new Role("ROLE_USER"));
        HashSet<Role> roles = new HashSet<>();
        Role role = roleService.getRoleById(1);
        roles.add(role);

            userEntityService.add(new UserEntity("admin", "admin", "admin", "admin"), roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}