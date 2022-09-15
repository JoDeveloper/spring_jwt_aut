package com.joecoder08.jwt.controller;

import com.joecoder08.jwt.entity.User;
import com.joecoder08.jwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @PostConstruct
    public void initRolesAndUsers(){
        userService.initRolesAndUser();
    }

    @PostMapping(path = "/registerNewUser")
    public User registerNewUser(@RequestBody User user){
        return userService.registerNewUser(user);
    }

    @GetMapping(path = "/forAdmin")
    public String forAdmin(){
        return "This Url is only accessible to admins";
    }


    @GetMapping(path = "/forUser")
    public String forUser(){
        return "This Url is only accessible to users";
    }
}
