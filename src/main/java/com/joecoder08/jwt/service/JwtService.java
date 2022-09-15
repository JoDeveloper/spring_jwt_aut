package com.joecoder08.jwt.service;

import com.joecoder08.jwt.dao.UserDao;
import com.joecoder08.jwt.entity.JwtRequest;
import com.joecoder08.jwt.entity.JwtResponse;
import com.joecoder08.jwt.entity.User;
import com.joecoder08.jwt.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class JwtService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;


    @Autowired
    private AuthenticationManager authenticationManager;


    public JwtResponse createJwtToken(JwtRequest request) throws Exception {

        authenticate(request.getUserName(), request.getUserPassword());

        final UserDetails userDetails = loadUserByUsername(request.getUserName());

        String token = jwtUtil.generateToken(userDetails);

        User user = userDao.findById(userDetails.getUsername()).get();

        return new JwtResponse(
                user,
                token
        );
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.findById(username).get();
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getUserName(),
                    user.getPassword(),
                    getAuthorities(user)
            );
        } else {
            throw new UsernameNotFoundException("userName is not valid");
        }

    }


    private Set getAuthorities(User user) {
        Set authorities = new HashSet();

        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
        });
        return authorities;
    }


    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("user is disabled");
        } catch (BadCredentialsException e) {
            throw new Exception("Bad credential from user");
        }
    }


}
