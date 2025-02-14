package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.dto.UserRegistrationRequestDto;
import com.musadzeyt.momentumapi.service.CustomUserDetailsService;
import com.musadzeyt.momentumapi.service.UserService;
import com.musadzeyt.momentumapi.util.JwtTokenUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final UserService userService;

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.get("username"), request.get("password")));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.get("username"));
        String token = jwtTokenUtil.generateToken(userDetails);
        return Map.of("token", token);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserRegistrationRequestDto request) {
        String message = userService.registerUser(request);
        return ResponseEntity.ok(Map.of("message", message));
    }
}
