package com.joecoder08.jwt.service;


import com.joecoder08.jwt.dao.RoleDao;
import com.joecoder08.jwt.dao.UserDao;
import com.joecoder08.jwt.entity.Role;
import com.joecoder08.jwt.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    public User registerNewUser(User user){
        return userDao.save(user);
    }

    public void initRolesAndUser(){
        Role adminRole = new Role();
        adminRole.setRoleName("Admin");
        adminRole.setRoleDescription("role for admins users only.");

        Role userRole = new Role();
        userRole.setRoleName("User");
        userRole.setRoleDescription("default role for newly users");

        roleDao.saveAll(List.of(adminRole));


        User admin = new User();
        admin.setUserName("Admin");
        admin.setUserName("admin");
        admin.setLastName("administrator");
        admin.setPassword(getEncodedPassword("admin1234"));
        admin.setRoles(Set.of(adminRole,userRole));

        User user = new User();
        user.setUserName("user");
        user.setUserName("user");
        user.setLastName("user");
        user.setPassword(getEncodedPassword("user1234"));
        user.setRoles(Set.of(userRole));

        userDao.saveAll(List.of(admin,user));


    }

    public String getEncodedPassword(String rawPassword){
        return bCryptPasswordEncoder.encode(rawPassword);
    }
}
