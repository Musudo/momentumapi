package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.record.UserLoginRequestRecord;
import com.musadzeyt.momentumapi.record.UserRegistrationRequestRecord;
import com.musadzeyt.momentumapi.service.CustomUserDetailsService;
import com.musadzeyt.momentumapi.service.UserService;
import com.musadzeyt.momentumapi.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserLoginRequestRecord request) {
        log.info("TEST" + request);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserRegistrationRequestRecord request) {
        String email = userService.registerUser(request);
        return new ResponseEntity<>(Map.of("email", email), HttpStatus.CREATED);
    }
}
