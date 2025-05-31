package com.microservices.auth.controller.auth;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.microservices.auth.model.ProviderEnum;
import com.microservices.auth.model.UserEntity;
import com.microservices.auth.model.dto.*;
import com.microservices.auth.security.UserDetailServiceImpl;
import com.microservices.auth.security.jwt.JwtUtils;
import com.microservices.auth.service.implementation.UserServiceImpl;
import com.microservices.auth.util.ResponseObject;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;


@RestController
@RequestMapping("/api/mcsv-auth/auth")
public class AuthController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public @ResponseBody ResponseEntity<ResponseObject> login(@RequestBody @Valid AuthLoginRequestDTO authRequest) {
        try {
            AuthResponseDTO authResponseDTO = this.userDetailService.loginUser(authRequest);
            return ResponseObject.build(true, HttpStatus.OK, "User logged in successfully", authResponseDTO);
        } catch (BadCredentialsException e) { // Usuario o contraseña incorrectos
            logger.error("BadCredentialsException: {}", e.getMessage());
            return ResponseObject.build(false, HttpStatus.UNAUTHORIZED, "Invalid username or password", null);
        } catch (DisabledException e) { // La cuenta está deshabilitada
            logger.error("DisabledException: {}", e.getMessage());
            return ResponseObject.build(false, HttpStatus.FORBIDDEN, "User account is disabled", null);
        } catch (LockedException e) { // La cuenta está bloqueada
            logger.error("LockedException: {}", e.getMessage());
            return ResponseObject.build(false, HttpStatus.LOCKED, "User account is locked", null);
        } catch (AuthenticationException e) { // Error de autenticación
            logger.error("AuthenticationException: {}", e.getMessage());
            return ResponseObject.build(false, HttpStatus.UNAUTHORIZED, "Authentication failed", null);
        } catch (Exception e) { // Error general
            logger.error("Exception: {}", e.getMessage());
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, "Ocurrió un error", null);
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody @Valid AuthCreateUserRequestDTO userRequest) {
        return new ResponseEntity<>(this.userDetailService.createUser(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/validate-token")
    public ResponseEntity<ResponseObject> validateToken(@RequestParam String token) {
        try {
            return ResponseObject.build(true, HttpStatus.OK, "Token is valid", this.userDetailService.extractUsernameFromToken(token));
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/login-oauth2/google")
    public ResponseEntity<ResponseObject> loginOAuth2Google(AuthLoginGoogleDTO authLoginGoogleDTO) {
        try {
            // Buscar el usuario en la base de datos
            UserEntity user = this.userService.getUserByEmail(authLoginGoogleDTO.getEmail());
            // Si el usuario no existe, se crea
            if (user == null) {
                user = this.userService.createUser(
                        new AuthCreateUserRequestDTO(
                                authLoginGoogleDTO.getEmail(),
                                passwordEncoder.encode(authLoginGoogleDTO.getId()),
                                authLoginGoogleDTO.getName(),
                                authLoginGoogleDTO.getEmail(), true,
                                ProviderEnum.GOOGLE,
                                new AuthCreateRoleRequestDTO(Collections.singletonList("USER"))));
                logger.info("User created: {}", user);
            }

            AuthResponseDTO authResponseDTO = this.userDetailService.loginUserOauth(user, authLoginGoogleDTO);
            return ResponseObject.build(true, HttpStatus.OK, "User logged in successfully", authResponseDTO);

        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }

    @PostMapping("/create-user")
    public ResponseEntity<ResponseObject> createUser(@RequestBody AuthCreateUserRequestDTO user) {
        try {
            UserEntity userCreated = userService.createUser(user);
            return ResponseObject.build(true, HttpStatus.CREATED, "User created successfully", userCreated);
        } catch (Exception e) {
            return ResponseObject.build(false, HttpStatus.BAD_REQUEST, e.getMessage(), null);
        }
    }


}
