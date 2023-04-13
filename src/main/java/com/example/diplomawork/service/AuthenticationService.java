package com.example.diplomawork.service;

import com.example.diplomawork.dto.request.AuthenticationRQ;
import com.example.diplomawork.dto.response.AuthenticationRS;
import com.example.diplomawork.jwt.JwtTokenProvider;
import com.example.diplomawork.model.User;
import com.example.diplomawork.repository.AuthenticationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthenticationService {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    AuthenticationRepository authenticationRepository;

    public AuthenticationRS login(AuthenticationRQ request) {
        String login = request.getLogin();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        login,
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();
        String jwtToken = jwtTokenProvider.generateToken(user);
        authenticationRepository.putTokenAndUsername(jwtToken, login);

        log.info("User {} authentication. JWT: {}", login, jwtToken);
        return new AuthenticationRS(jwtToken);
    }

    public void logout(String authToken) {
        final String jwtToken = authToken.split(" ")[1].trim();
        final String email = authenticationRepository.getUsernameByToken(jwtToken);
        log.info("User {} logout. JWT is disabled.",  email);
        authenticationRepository.removeTokenAndUsernameByToken(jwtToken);
    }
}
