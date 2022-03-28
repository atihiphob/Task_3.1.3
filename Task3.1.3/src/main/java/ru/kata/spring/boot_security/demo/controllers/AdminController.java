package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.UserEntity;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserEntityService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserEntityService userEntityService;
    private final RoleService roleService;
    @Autowired
    public AdminController(UserEntityService userEntityService, RoleService roleService) {
        this.userEntityService = userEntityService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAllUsers(Principal principal, Model model) {
        UserEntity user = userEntityService.findByUsername(principal.getName());
        model.addAttribute("new_user", new UserEntity());
        model.addAttribute("user", user);
        model.addAttribute("listRoles",roleService.getAllRoles());
        model.addAttribute("users", userEntityService.getAllUsers());
        return "admin/users";
    }

    @GetMapping("/user/{id}")
    public String getUser(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userEntityService.getUserById(id));
        return "user";
    }

    @GetMapping("/add")
    public String addUserForm (Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/add_user";
    }

    @PostMapping ("/add")
    public String addUser (UserEntity user, @RequestParam("roles") ArrayList<Integer> roles) {
        userEntityService.add(user, roleService.findRoles(roles));
        return  "redirect:/admin";
    }

    @GetMapping ("/{id}/edit")
    public String editUserForm(Model model, @PathVariable("id") int id) {
        model.addAttribute("user", userEntityService.getUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "/admin/edit_user";
    }

    @PatchMapping("/{id}")
    public String editUser (@ModelAttribute("user") UserEntity user, @PathVariable("id") int id, @RequestParam("roles")List <Integer> roles) {
        userEntityService.update(user, roleService.findRoles(roles));
        return "redirect:/admin";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) {
        userEntityService.remove(id);
        return "redirect:/admin";
    }

    @GetMapping("/user")
    public String showUser(Principal principal, Model model) {
        UserEntity user = userEntityService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("listRoles",roleService.getAllRoles());
        return "user";
    }
}
