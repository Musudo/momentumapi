package com.musadzeyt.momentumapi.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musadzeyt.momentumapi.domain.ExternalParticipant;
import com.musadzeyt.momentumapi.dto.entityDto.ExternalParticipantDto;
import com.musadzeyt.momentumapi.integration.AbstractIntegrationTestContainer;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import com.musadzeyt.momentumapi.repository.IExternalParticipantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
public class ExternalParticipantControllerTest extends AbstractIntegrationTestContainer {
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;
    @Autowired
    private IExternalParticipantRepository externalParticipantRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
        externalParticipantRepository.deleteAll();
    }

    @Test
    void findExternalParticipants_noJwt_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/external-participants"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findExternalParticipants_whenNoExternalParticipants_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/external-participants")
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void findExternalParticipants_withValidJwt_shouldReturnAllTags() throws Exception {
        ExternalParticipant externalParticipant1 = new ExternalParticipant();
        externalParticipant1.setName("Tom Cruise");
        externalParticipant1.setEmail("tom.cruise@email.com");
        externalParticipantRepository.save(externalParticipant1);

        ExternalParticipant externalParticipant2 = new ExternalParticipant();
        externalParticipant2.setName("Eddy Murphy");
        externalParticipant2.setEmail("eddy.murphy@email.com");
        externalParticipantRepository.save(externalParticipant2);

        ExternalParticipant externalParticipant3 = new ExternalParticipant();
        externalParticipant3.setName("Zinedine Zidane");
        externalParticipant3.setEmail("zinedine.zidane@email.com");
        externalParticipantRepository.save(externalParticipant3);

        mockMvc.perform(get("/api/external-participants")
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void createExternalParticipant_withValidJwt_shouldReturnNewExternalParticipant() throws Exception {
        ExternalParticipantDto externalParticipantDto = new ExternalParticipantDto();
        externalParticipantDto.setName("John Doe");
        externalParticipantDto.setEmail("john.doe@example.com");

        mockMvc.perform(post("/api/external-participants")
                        .header("Authorization", "Bearer " + authClient.getJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(externalParticipantDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

        mockMvc.perform(get("/api/external-participants")
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }
}
