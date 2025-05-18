package com.musadzeyt.momentumapi.service.entityService;

import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.domain.Institution;
import com.musadzeyt.momentumapi.domain.AppUser;
import com.musadzeyt.momentumapi.dto.entityDto.ContactDto;
import com.musadzeyt.momentumapi.dto.SearchCriteria;
import com.musadzeyt.momentumapi.exception.EntityNotFoundException;
import com.musadzeyt.momentumapi.repository.IContactRepository;
import com.musadzeyt.momentumapi.service.CustomUserDetailsService;
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
        SearchCriteria criteria = new SearchCriteria("app_user.email", ":", getUsername());
        return new ContactSpecification(criteria);
    }

    public List<Contact> findAll() {
        return contactRepository.findAll(getUsernameSpec());
    }

    public List<Contact> findAllById(Iterable<UUID> ids) {
        return contactRepository.findAllById(ids);
    }

    public List<Contact> findAllByInstitutionName(String name) {
        SearchCriteria institutionNameCriteria = new SearchCriteria("institution.name", ":", name);

        return contactRepository.findAll(getUsernameSpec().and(new ContactSpecification(institutionNameCriteria)));
    }

    public List<Contact> searchContacts(String searchParam) {
        SearchCriteria firstNameCriteria = new SearchCriteria("firstName", ":", searchParam);
        SearchCriteria lastNameCriteria = new SearchCriteria("lastName", ":", searchParam);
        SearchCriteria email1Criteria = new SearchCriteria("email1", ":", searchParam);
        SearchCriteria email2Criteria = new SearchCriteria("email2", ":", searchParam);
        SearchCriteria phone1Criteria = new SearchCriteria("phone1", ":", searchParam);
        SearchCriteria phone2Criteria = new SearchCriteria("phone2", ":", searchParam);
        SearchCriteria jobTitleCriteria = new SearchCriteria("jobTitle", ":", searchParam);

        Specification<Contact> orSpec = Specification
                .where(new ContactSpecification(firstNameCriteria))
                .or(new ContactSpecification(lastNameCriteria))
                .or(new ContactSpecification(email1Criteria))
                .or(new ContactSpecification(email2Criteria))
                .or(new ContactSpecification(phone1Criteria))
                .or(new ContactSpecification(phone2Criteria))
                .or(new ContactSpecification(jobTitleCriteria));

        return contactRepository.findAll(getUsernameSpec().and(orSpec));
    }

    public Contact findById(UUID id) {
        return contactRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Transactional
    public Contact create(ContactDto contactDto) {
        Contact contact = contactMapper.dtoToEntity(contactDto);
        AppUser user = customUserDetailsService.getCurrentUser();
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
