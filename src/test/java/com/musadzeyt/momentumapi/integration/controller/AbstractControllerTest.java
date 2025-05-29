package com.musadzeyt.momentumapi.integration.controller;

import com.musadzeyt.momentumapi.integration.AbstractTestContainer;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractControllerTest<T> extends AbstractTestContainer {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;

    protected abstract String getBaseRoute();

    protected abstract void eraseData();

    protected abstract void seedTestData();

    protected abstract T createSampleEntity() throws Exception;

    @BeforeEach
    void setUp() {
        eraseData();
    }

    void findEntities_noJwt_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get(getBaseRoute()))
                .andExpect(status().isUnauthorized());
    }

    void findEntities_whenNoEntities_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get(getBaseRoute())
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andDo(r -> {
                    System.out.println("STATUS: " + r.getResponse().getStatus());
                    System.out.println("BODY: " + r.getResponse().getContentAsString());
                })
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    void findEntities_shouldReturnAllEntities() throws Exception {
        seedTestData();

        // here comes custom logic for each controller test...
    }

    void findEntity_shouldReturnEntityById() throws Exception {
        T e = createSampleEntity();
        UUID id = (UUID) e.getClass().getMethod("getId").invoke(e);

        mockMvc.perform(get(getBaseRoute() + "/" + id)
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk());
    }

    void createEntity_shouldReturnNewEntity() throws Exception {
        // here comes custom logic for each controller test...
    }

    void updateEntity_shouldReturnUpdatedEntity() throws Exception {
        // here comes custom logic for each controller test...
    }

    void deleteEntity_shouldDeleteEntity() throws Exception {
        T e = createSampleEntity();
        UUID id = (UUID) e.getClass().getMethod("getId").invoke(e);

        mockMvc.perform(delete(getBaseRoute() + "/" + id)
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk());

        mockMvc.perform(get(getBaseRoute() + "/" + id)
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isNotFound());
    }
}
