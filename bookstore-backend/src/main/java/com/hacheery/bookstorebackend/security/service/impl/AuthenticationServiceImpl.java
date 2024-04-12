package com.hacheery.bookstorebackend.security.service.impl;

import com.hacheery.bookstorebackend.dto.UserDto;
import com.hacheery.bookstorebackend.exception.AuthenticationException;
import com.hacheery.bookstorebackend.exception.DuplicateException;
import com.hacheery.bookstorebackend.exception.SQLException;
import com.hacheery.bookstorebackend.mapper.UserMapper;
import com.hacheery.bookstorebackend.security.entity.*;
import com.hacheery.bookstorebackend.security.model.AuthenticationRequest;
import com.hacheery.bookstorebackend.security.model.AuthenticationResponse;
import com.hacheery.bookstorebackend.security.model.RegisterRequest;
import com.hacheery.bookstorebackend.security.repository.ConfirmationTokenRepository;
import com.hacheery.bookstorebackend.security.repository.TokenRepository;
import com.hacheery.bookstorebackend.security.repository.UserRepository;
import com.hacheery.bookstorebackend.security.service.AuthenticationService;
import com.hacheery.bookstorebackend.security.service.EmailService;
import com.hacheery.bookstorebackend.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        if(repository.existsByEmail(request.getEmail())) {
            throw new DuplicateException("Email already exists");
        }
        try {
            var user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.USER)
                    .enabled(false)
                    .build();
            var savedUser = repository.save(user);
            var jwtToken = jwtService.generateToken(savedUser);
            saveUserToken(savedUser, jwtToken);

            // send email verification
            ConfirmationToken confirmationToken = new ConfirmationToken(user);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Registration");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8080/api/auth/confirm-account?token="+confirmationToken.getConfirmationToken());
            emailService.sendEmail(mailMessage);
            System.out.println("Confirmation Token: " + confirmationToken.getConfirmationToken());

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new SQLException(e.getLocalizedMessage(), e);
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
            var user = repository.findByEmail(request.getEmail())
                    .orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);
            UserDto userDto = UserMapper.mapToUserDto(user);
            return AuthenticationResponse.builder()
                    .user(userDto)
                    .token(jwtToken)
                    .build();
        } catch (RuntimeException e) {
            throw new AuthenticationException(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> confirmEmail(String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null) {
            if(repository.findById(token.getTokenId()).isPresent()) {
                User user = repository.findById(token.getTokenId()).get();
                user.setEnabled(true);
                repository.save(user);
                return ResponseEntity.ok("Email verified successfully!");
            }
        }
        return ResponseEntity.badRequest().body("Error: Couldn't verify email");
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .userId(user.getId())
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }
}
