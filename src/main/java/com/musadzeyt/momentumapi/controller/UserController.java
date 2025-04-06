package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.dto.entity.UserDto;
import com.musadzeyt.momentumapi.service.UserService;
import com.musadzeyt.momentumapi.util.mapper.IUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class UserController {
    private final UserService userService;

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<UserDto> updateUser(@PathVariable UUID id, @RequestBody UserDto userDto) {
        return new ResponseEntity<>(IUserMapper.INSTANCE.entityToDto(userService.update(id, userDto)), HttpStatus.OK);
    }
}
