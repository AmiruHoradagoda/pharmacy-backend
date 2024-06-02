package com.project.pharmacy_backend.service;

import com.project.pharmacy_backend.dto.request.LoginRequest;
import com.project.pharmacy_backend.dto.response.LoginResponse;
import com.project.pharmacy_backend.entity.User;
import com.project.pharmacy_backend.repo.UserRepo;
import com.project.pharmacy_backend.util.JwtUtil;
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
    private UserRepo userRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    public LoginResponse createJwtToken(LoginRequest loginRequest) throws Exception {
        String userName = loginRequest.getUserEmail();
        String userPassword = loginRequest.getUserPassword();
        authenticate(userName, userPassword);
        UserDetails userDetails = loadUserByUsername(userName);

        String newGeneratedToken = jwtUtil.generateToken(userDetails);
        User user = userRepo.findById(userName).get();
        System.out.println(user);

        LoginResponse loginResponse = new LoginResponse(
                user,
                newGeneratedToken
        );
        return loginResponse;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findById(username).get();

        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    getAuthority(user)
            );

        } else {
            throw new UsernameNotFoundException("User not found with userName" + username);
        }
    }


    private Set getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        user.getRole().forEach(role -> {
            authorities.add((new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));
        });
        return authorities;
    }

    private void authenticate(String userName, String userPassword) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, userPassword));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}