package com.musadzeyt.momentumapi.integration.controller;

import com.musadzeyt.momentumapi.dataFaker.generator.ContactGenerator;
import com.musadzeyt.momentumapi.domain.Contact;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import com.musadzeyt.momentumapi.repository.IContactRepository;
import com.musadzeyt.momentumapi.service.entityService.ContactService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ContactControllerTest extends AbstractControllerTest<Contact> {
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;
    @Autowired
    private IContactRepository contactRepository;
    @Autowired
    private ContactGenerator contactGenerator;
    @Autowired
    private ContactService contactService;

    @Override
    protected String getBaseRoute() {
        return "/api/contacts";
    }

    @Override
    protected void deleteAllEntities() {
        contactRepository.deleteAll();
    }

    @Override
    protected List<Contact> createSampleEntities() {
        return contactGenerator.createContacts(10);
    }
}
