package com.example.demo.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.common.enums.RoleEnum;
import com.example.demo.common.exception.NotFoundException;
import com.example.demo.dto.authentication.LoginUserDTO;
import com.example.demo.dto.authentication.RegisterUserDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public User signup(RegisterUserDTO input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        var user = User.builder()
		.email(input.getEmail())
		.username(input.getUsername())
		.password(passwordEncoder.encode(input.getPassword()))
		.role(optionalRole.get())
		.build();

        return userRepository.save(user);
    }

    public User authenticate(LoginUserDTO input) {
        log.info("authenticate");
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                input.getUsername(),
                input.getPassword()
            )
        );
        log.info("authentication manager success");
        return userRepository.findByUsername(input.getUsername()).orElseThrow(() -> new NotFoundException("User with username: "+input.getUsername()+" not found"));
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }
}
