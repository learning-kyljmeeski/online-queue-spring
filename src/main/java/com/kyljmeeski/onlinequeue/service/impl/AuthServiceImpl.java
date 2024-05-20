package com.kyljmeeski.onlinequeue.service.impl;

import com.kyljmeeski.onlinequeue.entity.User;
import com.kyljmeeski.onlinequeue.exception.UsernameIsTakenException;
import com.kyljmeeski.onlinequeue.model.request.LoginRequest;
import com.kyljmeeski.onlinequeue.model.request.SignupRequest;
import com.kyljmeeski.onlinequeue.model.response.LoginResponse;
import com.kyljmeeski.onlinequeue.repository.UserRepository;
import com.kyljmeeski.onlinequeue.security.JwtUtil;
import com.kyljmeeski.onlinequeue.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );
            User user = new User(authentication.getName());
            return ResponseEntity.ok(
                    new LoginResponse(user.getUsername(), jwtUtil.createToken(user), jwtUtil.tokenValidity())
            );
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> signup(SignupRequest signupRequest) {
        if (userRepository.findByUsername(signupRequest.username()).isPresent()) {
            throw new UsernameIsTakenException(signupRequest.username());
        }
        User user = userRepository.save(
                new User(
                        signupRequest.username(),
                        passwordEncoder.encode(signupRequest.password()),
                        signupRequest.name()
                )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new LoginResponse(user.getUsername(), jwtUtil.createToken(user), jwtUtil.tokenValidity())
        );
    }
}
