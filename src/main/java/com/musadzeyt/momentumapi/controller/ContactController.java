package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.dto.ContactDto;
import com.musadzeyt.momentumapi.service.ContactService;
import com.musadzeyt.momentumapi.util.mapper.IContactMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/contacts")
@AllArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @GetMapping("")
    public ResponseEntity<List<ContactDto>> findContacts() {
        return new ResponseEntity<>(contactService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> findContact(@PathVariable UUID id) {
        return new ResponseEntity<>(contactService.findById(id), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto) {
        var contact = contactService.create(contactDto);
        return new ResponseEntity<>(IContactMapper.INSTANCE.entityToDto(contact), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ContactDto> updateContact(@PathVariable UUID id, @NotNull @Valid @RequestBody ContactDto contactDto) {
        var contact = contactService.update(id, contactDto);
        return new ResponseEntity<>(IContactMapper.INSTANCE.entityToDto(contact), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable UUID id) {
        contactService.delete(id);
        return new ResponseEntity<>("Contact deleted", HttpStatus.OK);
    }
}
