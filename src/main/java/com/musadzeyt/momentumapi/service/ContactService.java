package com.musadzeyt.momentumapi.service;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.domain.Institution;
import com.musadzeyt.momentumapi.domain.User;
import com.musadzeyt.momentumapi.dto.ContactDto;
import com.musadzeyt.momentumapi.dto.SearchCriteria;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IContactRepository;
import com.musadzeyt.momentumapi.specification.ContactSpecification;
import com.musadzeyt.momentumapi.util.mapper.IContactMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ContactService {
    private final IContactRepository contactRepository;
    private final IContactMapper contactMapper;
    private final InstitutionService institutionService;
    private final CustomUserDetailsService customUserDetailsService;

    private String getUsername() {
        return customUserDetailsService.getCurrentUsername();
    }

    private Specification<Contact> getUsernameSpec() {
        SearchCriteria criteria = new SearchCriteria("user.email", ":", getUsername());
        return new ContactSpecification(criteria);
    }

    public List<Contact> findAll() {
        return contactRepository.findAll(getUsernameSpec());
    }

    public List<Contact> findAllByInstitutionName(String name) {
        SearchCriteria institutionNameCriteria = new SearchCriteria("institution.name", ":", name);

        return contactRepository.findAll(getUsernameSpec().and(new ContactSpecification(institutionNameCriteria)));
    }

    public Contact findById(UUID id) {
        return contactRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Contact create(ContactDto contactDto) {
        Contact contact = contactMapper.dtoToEntity(contactDto);
        User user = customUserDetailsService.getCurrentUser();
        contact.setUser(user);
        Institution institution = institutionService.findById(contactDto.getInstitutionId());
        contact.setInstitution(institution);
        return contactRepository.save(contact);
    }

    @Transactional
    public Contact update(UUID id, ContactDto contactDto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        contactMapper.update(contactDto, contact);
        return contactRepository.save(contact);
    }

    @Transactional
    public void delete(UUID id) {
        contactRepository.deleteById(id);
    }
}
