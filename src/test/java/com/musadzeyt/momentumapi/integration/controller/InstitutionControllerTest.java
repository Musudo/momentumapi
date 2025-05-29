package com.musadzeyt.momentumapi.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musadzeyt.momentumapi.dataFaker.DataSeeder;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.InstitutionDto;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import com.musadzeyt.momentumapi.service.entityService.AppUserService;
import com.musadzeyt.momentumapi.util.mapper.AppUserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InstitutionControllerTest extends AbstractControllerTest<InstitutionDto> {
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;
    @Autowired
    private DataSeeder dataSeeder;
    @Autowired
    private AppUserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected String getBaseRoute() {
        return "/api/v1/institutions";
    }

    @Override
    protected void eraseData() {
        dataSeeder.eraseData();
    }

    @Override
    protected void seedTestData() {
        dataSeeder.seedTestData();
    }

    @Override
    protected InstitutionDto createSampleEntity() throws Exception {
        InstitutionDto institutionDto = new InstitutionDto();
        institutionDto.setName("Test institution");
        institutionDto.setStreet("Test street");
        institutionDto.setBuildingNumber("123");
        institutionDto.setCity("Antwerp");
        institutionDto.setCountryCode("BE");
        institutionDto.setPostalCode("2000");
        institutionDto.setPostbox("1");
        institutionDto.setUser(AppUserMapper.INSTANCE.entityToDto(userService.findByEmail("guest@email.com")));

        MvcResult result = mockMvc.perform(post(getBaseRoute())
                        .header("Authorization", "Bearer " + authClient.getJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(institutionDto)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), InstitutionDto.class);
    }

    @Test
    void findInstitutions_noJwt_shouldReturnUnauthorized() throws Exception {
        super.findEntities_noJwt_shouldReturnUnauthorized();
    }

    @Test
    void findInstitutions_whenNoInstitutions_shouldReturnEmptyList() throws Exception {
        super.findEntities_whenNoEntities_shouldReturnEmptyList();
    }

    @Test
    void findInstitutions_shouldReturnAllInstitutions() throws Exception {
        super.findEntities_shouldReturnAllEntities();

        mockMvc.perform(get(getBaseRoute())
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void findInstitution_shouldReturnInstitutionById() throws Exception {
        super.findEntity_shouldReturnEntityById();
    }

    @Test
    void createInstitution_shouldReturnNewInstitution() throws Exception {
        super.createEntity_shouldReturnNewEntity();

        InstitutionDto institutionDto = createSampleEntity();

        mockMvc.perform(get(getBaseRoute() + "/" + institutionDto.getId())
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test institution"))
                .andExpect(jsonPath("$.street").value("Test street"));
    }

    @Test
    void updateInstitution_shouldReturnUpdatedInstitution() throws Exception {
        super.updateEntity_shouldReturnUpdatedEntity();

        InstitutionDto institutionDto = createSampleEntity();

        institutionDto.setName("New name");
        institutionDto.setStreet("New street");

        mockMvc.perform(patch(getBaseRoute() + "/" + institutionDto.getId())
                        .header("Authorization", "Bearer " + authClient.getJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(institutionDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(institutionDto.getId().toString()))
                .andExpect(jsonPath("$.name").value("New name"))
                .andExpect(jsonPath("$.street").value("New street"));
    }

    @Test
    void deleteInstitution_shouldDeleteInstitution() throws Exception {
        super.deleteEntity_shouldDeleteEntity();
    }
}
