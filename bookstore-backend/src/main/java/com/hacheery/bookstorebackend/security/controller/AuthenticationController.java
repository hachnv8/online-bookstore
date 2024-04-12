package com.hacheery.bookstorebackend.security.controller;

import com.hacheery.bookstorebackend.security.model.AuthenticationRequest;
import com.hacheery.bookstorebackend.security.model.AuthenticationResponse;
import com.hacheery.bookstorebackend.security.model.RegisterRequest;
import com.hacheery.bookstorebackend.security.service.impl.AuthenticationServiceImpl;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthenticationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
    private final AuthenticationServiceImpl service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        logger.info(String.format("Register new user: username = %s, password = %s, email = %s",
                request.getName(), request.getPassword(), request.getEmail()));
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        logger.info(String.format("email = %s, password = %s",
                request.getEmail(), request.getPassword()));
        return ResponseEntity.ok(service.authenticate(request));
    }

    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<?> confirmUserAccount(@RequestParam("token") String confirmationToken) {
        return service.confirmEmail(confirmationToken);
    }

}
