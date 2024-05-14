package com.kyljmeeski.onlinequeue.service;

import com.kyljmeeski.onlinequeue.model.request.LoginRequest;
import com.kyljmeeski.onlinequeue.model.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> login(LoginRequest loginRequest);

    ResponseEntity<?> signup(SignupRequest signupRequest);
}
