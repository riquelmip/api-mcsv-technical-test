package com.microservices.auth.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.microservices.auth.model.RoleEntity;
import com.microservices.auth.model.UserEntity;
import com.microservices.auth.model.dto.*;
import com.microservices.auth.repository.RoleRepository;
import com.microservices.auth.repository.UserRepository;
import com.microservices.auth.security.jwt.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
        userEntity.getRoles().forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName().name()))));
        userEntity.getRoles().stream()
                .flatMap(role -> role.getRolePermissions().stream())
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));


        log.info("AuthorityList: {}", authorityList);
        log.info("User: {}", userEntity.getUsername());
        log.info("password: {}", userEntity.getPassword());
        log.info("isEnable: {}", userEntity.isEnable());
        log.info("accountNonExpired: {}", userEntity.isAccountNonExpired());
        log.info("credentialsNonExpired: {}", userEntity.isCredentialsNonExpired());
        log.info("accountNonLocked: {}", userEntity.isAccountNonLocked());
        return new User(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.isEnable(),
                userEntity.isAccountNonExpired(),
                userEntity.isCredentialsNonExpired(),
                userEntity.isAccountNonLocked(),
                authorityList);

    }

    public AuthResponseDTO createUser(AuthCreateUserRequestDTO createRoleRequest) {

        String username = createRoleRequest.username();
        String password = createRoleRequest.password();
        List<String> rolesRequest = createRoleRequest.roles().list();

        Set<RoleEntity> roleEntityList = new HashSet<>(roleRepository.findByRoleNameIn(rolesRequest));

        if (roleEntityList.isEmpty()) {
            throw new IllegalArgumentException("The roles specified does not exist.");
        }

        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .roles(roleEntityList)
                .isEnable(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();

        UserEntity userSaved = userRepository.save(userEntity);

        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userSaved.getRoles()
                .forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRoleName().name()))));

        userSaved.getRoles().stream()
                .flatMap(role -> role.getRolePermissions().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getPermissionName())));

        SecurityContext securityContextHolder = SecurityContextHolder.getContext();
        Authentication authentication = new UsernamePasswordAuthenticationToken(userSaved, null, authorities);

        String accessToken = jwtUtils.createToken(authentication);

        AuthResponseDTO authResponse = new AuthResponseDTO(username, "User created successfully", accessToken, true);
        return authResponse;
    }

    // Método para loguear un usuario local
    public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest) {
        log.info("AuthLoginRequest: {}", authLoginRequest.password());
        String username = authLoginRequest.username();
        String password = authLoginRequest.password();


        Authentication authentication = this.authenticate(username, password);
        log.info("Authentication: {}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        log.info("AccessToken: {}", accessToken);
        return new AuthResponseDTO(username, "User logged in successfully", accessToken, true);
    }

    // Método para loguear un usuario OAuth2
    public AuthResponseDTO loginUserOauth(UserEntity userEntity, AuthLoginGoogleDTO authLoginGoogleDTO) {
        String email = userEntity.getEmail();

        log.info("Logueando por loginUserOauth para {}", email);

        // Autenticación sin verificar contraseña
        Authentication authentication = this.authenticateOauth(email, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createTokenOAuth2(userEntity);
        return new AuthResponseDTO(email, "User logged in successfully", accessToken, true);
    }

    // Método para autenticar un usuario local
    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

    // Método para autenticar un usuario OAuth2
    public Authentication authenticateOauth(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username");
        }

        log.info("Usuario autenticado correctamente con OAuth2: {}", username);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }


    public boolean validateToken(String token) {
        try {
            jwtUtils.validateToken(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public ExtractUsernameFromTokenResponse extractUsernameFromToken(String token) {
        try {
            DecodedJWT decodedJWT = jwtUtils.validateToken(token);
            String username = decodedJWT.getSubject();
            return new ExtractUsernameFromTokenResponse(username, token);
        } catch (JWTVerificationException e) {
            return new ExtractUsernameFromTokenResponse(null, null);
        }
    }
}
