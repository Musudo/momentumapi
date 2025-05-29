package com.musadzeyt.momentumapi.api.v1.controller;

import com.musadzeyt.momentumapi.api.v1.dto.entityDto.AppUserDto;
import com.musadzeyt.momentumapi.record.UserLoginRequest;
import com.musadzeyt.momentumapi.record.UserRegistrationRequest;
import com.musadzeyt.momentumapi.service.CustomUserDetailsService;
import com.musadzeyt.momentumapi.service.entityService.AppUserService;
import com.musadzeyt.momentumapi.util.JwtTokenUtil;
import com.musadzeyt.momentumapi.util.mapper.AppUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Tag(name = "Authentication", description = "Operations related to user authentication")
@Slf4j
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final CustomUserDetailsService userDetailsService;
    private final AppUserService userService;

    @PostMapping("/login")
    @Operation(summary = "Authenticate user", description = "Logs in a user with email and password")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody UserLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.username());
        String token = jwtTokenUtil.generateToken(userDetails);
        Date expirationDate = jwtTokenUtil.getExpirationDateFromToken(token);
        AppUserDto userDto = AppUserMapper.INSTANCE.entityToDto(userService.findByEmail(userDetails.getUsername()));
        return ResponseEntity.ok(Map.of("token", token, "expirationDate", expirationDate.toString(), "user", userDto));
    }

    @PostMapping("/register")
    @Operation(summary = "Register user", description = "Creates a new user with given email and password")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserRegistrationRequest request) {
        String email = userService.registerUser(request);
        return new ResponseEntity<>(Map.of("email", email), HttpStatus.CREATED);
    }
}
