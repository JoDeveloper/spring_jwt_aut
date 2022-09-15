package com.joecoder08.jwt.service;


import com.joecoder08.jwt.dao.RoleDao;
import com.joecoder08.jwt.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRole(Role role){
        return roleDao.save(role);
    }
}
