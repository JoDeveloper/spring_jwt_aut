package com.joecoder08.jwt.controller;

import com.joecoder08.jwt.entity.JwtRequest;
import com.joecoder08.jwt.entity.JwtResponse;
import com.joecoder08.jwt.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class JwtController {
    @Autowired
    JwtService jwtService;


    @PostMapping(path = "/authenticate")
    public JwtResponse authenticate(@RequestBody JwtRequest request) throws  Exception{
        return jwtService.createJwtToken(request);
    }
}
