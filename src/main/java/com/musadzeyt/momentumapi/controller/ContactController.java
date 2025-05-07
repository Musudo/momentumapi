package com.musadzeyt.momentumapi.controller;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.dto.entityDto.ContactDto;
import com.musadzeyt.momentumapi.service.entityService.ContactService;
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
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ContactController {
    private final ContactService contactService;
    private final IContactMapper contactMapper;

    @GetMapping("")
    public ResponseEntity<List<ContactDto>> findContacts() {
        return new ResponseEntity<>(contactMapper.entityListToDtoList(contactService.findAll()), HttpStatus.OK);
    }

    @GetMapping("/by-institution-name/{name}")
    public ResponseEntity<List<ContactDto>> findContactsByInstitutionName(@PathVariable String name) {
        return new ResponseEntity<>(contactMapper.entityListToDtoList(contactService.findAllByInstitutionName(name)), HttpStatus.OK);
    }

    @GetMapping("/search-contacts/{searchParam}")
    public ResponseEntity<List<ContactDto>> searchContacts(@PathVariable String searchParam) {
        return new ResponseEntity<>(contactMapper.entityListToDtoList(contactService.searchContacts(searchParam)), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> findContact(@PathVariable UUID id) {
        return new ResponseEntity<>(contactMapper.entityToDto(contactService.findById(id)), HttpStatus.OK);
    }

    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto) {
        Contact contact = contactService.create(contactDto);
        return new ResponseEntity<>(contactMapper.entityToDto(contact), HttpStatus.OK);
    }

    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ContactDto> updateContact(@PathVariable UUID id, @NotNull @Valid @RequestBody ContactDto contactDto) {
        var contact = contactService.update(id, contactDto);
        return new ResponseEntity<>(contactMapper.entityToDto(contact), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable UUID id) {
        contactService.delete(id);
        return new ResponseEntity<>("Contact deleted", HttpStatus.OK);
    }
}
