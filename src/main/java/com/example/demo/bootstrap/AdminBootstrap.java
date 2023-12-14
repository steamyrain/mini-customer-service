package com.example.demo.bootstrap;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.example.demo.common.enums.RoleEnum;
import com.example.demo.dto.authentication.RegisterUserDTO;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.AllArgsConstructor;

import java.util.Optional;

@Component
@AllArgsConstructor
public class AdminBootstrap implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createAdmin();
    }

    private void createAdmin() {
        RegisterUserDTO userDto = RegisterUserDTO.builder()
		.email("admin@mail.com")
		.username("admin")
		.password("master")
		.build();

        Optional<Role> optionalRole = roleRepository.findByName(RoleEnum.ADMIN);
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        var user = User.builder() 
		.username(userDto.getUsername())
		.email(userDto.getEmail())
		.password(passwordEncoder.encode(userDto.getPassword()))
		.role(optionalRole.get())
		.build();

        userRepository.save(user);
    }
}
