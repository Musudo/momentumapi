package com.musadzeyt.momentumapi.integration.controller;

import com.musadzeyt.momentumapi.integration.AbstractTestContainer;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class AbstractControllerTest<T> extends AbstractTestContainer {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;

    protected abstract String getBaseRoute();

    protected abstract void deleteAllEntities();

    protected abstract List<T> createSampleEntities();

    @BeforeEach
    void setUp() {
        deleteAllEntities();
    }

    void findEntities_noJwt_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get(getBaseRoute()))
                .andExpect(status().isUnauthorized());
    }

    void findEntities_whenNoEntities_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get(getBaseRoute())
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    void findEntities_withValidJwt_shouldReturnAllEntities() throws Exception {
        createSampleEntities();

        // here comes custom logic for each controller test...
    }
}
