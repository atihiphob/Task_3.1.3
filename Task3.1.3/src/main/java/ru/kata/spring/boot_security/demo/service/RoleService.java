package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    void add (Role role);
    Set<Role> findRoles(List<Integer> roles);
    Role getRoleByName(String roleName);
    Role getRoleById(int id);
    List<Role>getAllRoles();
}
