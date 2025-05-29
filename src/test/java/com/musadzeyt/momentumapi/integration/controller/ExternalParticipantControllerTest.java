package com.musadzeyt.momentumapi.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musadzeyt.momentumapi.dataFaker.DataSeeder;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.ExternalParticipantDto;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
public class ExternalParticipantControllerTest extends AbstractControllerTest<ExternalParticipantDto> {
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private DataSeeder dataSeeder;

    @Override
    protected String getBaseRoute() {
        return "/api/v1/external-participants";
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
    protected ExternalParticipantDto createSampleEntity() throws Exception {
        ExternalParticipantDto externalParticipantDto = new ExternalParticipantDto();
        externalParticipantDto.setName("John Doe");
        externalParticipantDto.setEmail("john.doe@example.com");

        MvcResult result = mockMvc.perform(post(getBaseRoute())
                        .header("Authorization", "Bearer " + authClient.getJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(externalParticipantDto)))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), ExternalParticipantDto.class);
    }

    @Test
    void findExternalParticipants_noJwt_shouldReturnUnauthorized() throws Exception {
        super.findEntities_noJwt_shouldReturnUnauthorized();
    }

    @Test
    void findExternalParticipants_whenNoExternalParticipants_shouldReturnEmptyList() throws Exception {
        super.findEntities_whenNoEntities_shouldReturnEmptyList();
    }

    @Test
    void findExternalParticipants_shouldReturnAllExternalParticipants() throws Exception {
        super.findEntities_shouldReturnAllEntities();

        mockMvc.perform(get(getBaseRoute())
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(18));
    }
}
