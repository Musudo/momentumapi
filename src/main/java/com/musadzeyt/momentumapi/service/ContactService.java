package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.dto.ContactDto;
import com.musadzeyt.momentumapi.exception.GeneralException;
import com.musadzeyt.momentumapi.repository.IContactRepository;
import com.musadzeyt.momentumapi.util.mapper.IContactMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ContactService {
    private final IContactRepository contactRepository;
    private final IContactMapper contactMapper;

    public List<ContactDto> findAll() {
        List<Contact> contacts = contactRepository.findAll();
        return contactMapper.entityListToDtoList(contacts);
    }

    public ContactDto findById(UUID id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(GeneralException::new);
        return contactMapper.entityToDto(contact);
    }

    public Contact create(ContactDto contactDto) {
        Contact contact = contactMapper.dtoToEntity(contactDto);
        return contactRepository.save(contact);
    }

    public Contact update(UUID id, ContactDto contactDto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(GeneralException::new);
        contactMapper.update(contactDto, contact);
        return contactRepository.save(contact);
    }

    public void delete(UUID id) {
        contactRepository.deleteById(id);
    }
}
