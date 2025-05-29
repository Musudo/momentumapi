package com.musadzeyt.momentumapi.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musadzeyt.momentumapi.dataFaker.DataSeeder;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.ContactDto;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import com.musadzeyt.momentumapi.repository.AppUserRepository;
import com.musadzeyt.momentumapi.repository.InstitutionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContactControllerTest extends AbstractControllerTest<ContactDto> {
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;
    @Autowired
    private InstitutionRepository institutionRepository;
    @Autowired
    private AppUserRepository userRepository;
    @Autowired
    private DataSeeder dataSeeder;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected String getBaseRoute() {
        return "/api/v1/contacts";
    }

    @Override
    protected void eraseData() {
    }

    @Override
    protected void seedTestData() {
    }

    @Override
    protected ContactDto createSampleEntity() throws Exception {
//        ContactDto contactDto = new ContactDto();
//        contactDto.setFirstName("Test contact");
//        contactDto.setLastName("abcd");
//        contactDto.setJobTitle("Test title");
//        contactDto.setEmail1("test@email.com");
//        contactDto.setPhone1("12345678");
//        contactDto.setInstitutionId(institutionRepository.findAll().getFirst().getId());
//        contactDto.setUserId(userRepository.findAll().getFirst().getId());
//
//        MvcResult result = mockMvc.perform(post(getBaseRoute())
//                        .header("Authorization", "Bearer " + authClient.getJwtToken())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(contactDto)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        return objectMapper.readValue(result.getResponse().getContentAsString(), ContactDto.class);
        return null;
    }

    @Test
    void findContacts_noJwt_shouldReturnUnauthorized() throws Exception {
        super.findEntities_noJwt_shouldReturnUnauthorized();
    }

    @Test
    void findContacts_whenNoContacts_shouldReturnEmptyList() throws Exception {
        super.findEntities_whenNoEntities_shouldReturnEmptyList();
    }

    @Test
    void findContacts_shouldReturnAllContacts() throws Exception {
        super.findEntities_shouldReturnAllEntities();

        mockMvc.perform(get(getBaseRoute())
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
        // TODO: work out this further later
    }
}
