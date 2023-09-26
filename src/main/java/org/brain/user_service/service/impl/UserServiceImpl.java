package org.brain.user_service.service.impl;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.brain.user_service.config.JwtProperties;
import org.brain.user_service.exceptionHandler.exceptions.EntityNotFoundException;
import org.brain.user_service.model.User;
import org.brain.user_service.payload.response.LoginResponse;
import org.brain.user_service.repository.UserRepository;
import org.brain.user_service.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public record UserServiceImpl(UserRepository userRepository,JwtProperties jwtProperties) implements UserService {
    private static final String ACCOUNT_ALREADY_EXISTS = "Account with this email already exists!";

    public User signUp(User user) throws EntityNotFoundException {
        // todo: check if email valid
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EntityNotFoundException(ACCOUNT_ALREADY_EXISTS);
        }
        // todo: encode password
        // todo: add roles
        // todo: confirm email
        return userRepository.save(user);
    }

    @Override
    public LoginResponse logIn(User user) throws EntityNotFoundException {
        log.info("processing login");
        //todo: provide token
        var token = "token";
        //todo: encode password
        User localUser = userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new EntityNotFoundException(
                "User not found with email = " + user.getEmail()));
        if (!user.getPassword().equals(localUser.getPassword()))
            throw new EntityNotFoundException("Credentials not valid for email = " + user.getEmail());
        return new LoginResponse(localUser.getId(), user.getEmail(), token);
    }



    private String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        String email = userDetails.getUsername();
        List<String> roleList = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        claims.put("roles", roleList);
        claims.put("email", email);

        Date issuedDate = new Date();
        Date expiredDate =new Date(issuedDate.getTime() + jwtProperties.lifetime());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(issuedDate)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS256, jwtProperties.secret())
                .compact();
    }
}
