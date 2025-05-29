package com.musadzeyt.momentumapi.api.v1.controller;

import com.musadzeyt.momentumapi.api.v1.dto.entityDto.AppUserDto;
import com.musadzeyt.momentumapi.service.entityService.AppUserService;
import com.musadzeyt.momentumapi.util.mapper.AppUserMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "User", description = "Operations related to users")
@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AppUserController {
    private final AppUserService userService;

    @Operation(summary = "Update a user")
    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<AppUserDto> updateUser(@PathVariable UUID id, @RequestBody AppUserDto userDto) {
        return new ResponseEntity<>(AppUserMapper.INSTANCE.entityToDto(userService.update(id, userDto)), HttpStatus.OK);
    }
}
