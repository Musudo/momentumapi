package com.musadzeyt.momentumapi.api.v1.controller;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.ContactDto;
import com.musadzeyt.momentumapi.service.entityService.ContactService;
import com.musadzeyt.momentumapi.util.mapper.ContactMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Contact", description = "Operations related to contacts")
@RestController
@RequestMapping("/api/v1/contacts")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ContactController {
    private final ContactService contactService;
    private final ContactMapper contactMapper;

    @Operation(summary = "Get all contacts", description = "Returns a list of all contacts.")
    @GetMapping("")
    public ResponseEntity<List<ContactDto>> findContacts() {
        return new ResponseEntity<>(contactMapper.entityListToDtoList(contactService.findAll()), HttpStatus.OK);
    }

    @Operation(summary = "Get contacts by institution name", description = "Get contacts by its related institution name")
    @GetMapping("/by-institution-name/{name}")
    public ResponseEntity<List<ContactDto>> findContactsByInstitutionName(@PathVariable String name) {
        return new ResponseEntity<>(contactMapper.entityListToDtoList(contactService.findAllByInstitutionName(name)), HttpStatus.OK);
    }

    @Operation(summary = "Search contacts")
    @GetMapping("/search-contacts/{searchParam}")
    public ResponseEntity<List<ContactDto>> searchContacts(@PathVariable String searchParam) {
        return new ResponseEntity<>(contactMapper.entityListToDtoList(contactService.searchContacts(searchParam)), HttpStatus.OK);
    }

    @Operation(summary = "Get a contact by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> findContact(@PathVariable UUID id) {
        return new ResponseEntity<>(contactMapper.entityToDto(contactService.findById(id)), HttpStatus.OK);
    }

    @Operation(summary = "Create a new contact")
    @PostMapping(value = "", produces = "application/json")
    public ResponseEntity<ContactDto> createContact(@RequestBody ContactDto contactDto) {
        Contact contact = contactService.create(contactDto);
        return new ResponseEntity<>(contactMapper.entityToDto(contact), HttpStatus.OK);
    }

    @Operation(summary = "Update a contact")
    @PatchMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<ContactDto> updateContact(@PathVariable UUID id, @NotNull @Valid @RequestBody ContactDto contactDto) {
        var contact = contactService.update(id, contactDto);
        return new ResponseEntity<>(contactMapper.entityToDto(contact), HttpStatus.OK);
    }

    @Operation(summary = "Delete a contact")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable UUID id) {
        contactService.delete(id);
        return new ResponseEntity<>("Contact deleted", HttpStatus.OK);
    }
}
