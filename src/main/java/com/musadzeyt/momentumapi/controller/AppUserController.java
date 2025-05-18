package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.dto.entityDto.AppUserDto;
import com.musadzeyt.momentumapi.service.entityService.AppUserService;
import com.musadzeyt.momentumapi.util.mapper.IAppUserMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class AppUserController {
    private final AppUserService userService;

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<AppUserDto> updateUser(@PathVariable UUID id, @RequestBody AppUserDto userDto) {
        return new ResponseEntity<>(IAppUserMapper.INSTANCE.entityToDto(userService.update(id, userDto)), HttpStatus.OK);
    }
}
