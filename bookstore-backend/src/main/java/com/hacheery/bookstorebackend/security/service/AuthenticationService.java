package com.hacheery.bookstorebackend.security.service;

import com.hacheery.bookstorebackend.security.model.AuthenticationRequest;
import com.hacheery.bookstorebackend.security.model.AuthenticationResponse;
import com.hacheery.bookstorebackend.security.model.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);

    ResponseEntity<?> confirmEmail(String confirmationToken);
}
