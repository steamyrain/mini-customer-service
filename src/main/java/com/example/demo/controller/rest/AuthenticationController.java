package com.example.demo.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.authentication.LoginUserDTO;
import com.example.demo.dto.authentication.LoginUserResponseDTO;
import com.example.demo.dto.authentication.RegisterUserDTO;
import com.example.demo.entity.User;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody RegisterUserDTO registerUserDto) {
        log.info("signup");
        User registeredUser = authenticationService.signup(registerUserDto);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDTO> authenticate(@RequestBody LoginUserDTO loginUserDto) {
        log.info("login");
        User authenticatedUser = authenticationService.authenticate(loginUserDto);
        log.info("User login: {}", authenticatedUser.getUsername());
        String jwtToken = jwtService.generateToken(authenticatedUser);
        LoginUserResponseDTO loginUserResponseDTO = LoginUserResponseDTO.builder()
            .token(jwtToken)
            .expiresIn(jwtService.getExpirationTime())
            .build();
        return ResponseEntity.ok(loginUserResponseDTO);
    }
}
