package com.musadzeyt.momentumapi.integration.controller;

import com.musadzeyt.momentumapi.dataFaker.DataSeeder;
import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TagControllerTest extends AbstractControllerTest<Tag> {
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;
    @Autowired
    private DataSeeder dataSeeder;

    @Override
    protected String getBaseRoute() {
        return "/api/v1/tags";
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
    protected Tag createSampleEntity() throws Exception {
        return null;
    }

    @Test
    void findTags_noJwt_shouldReturnUnauthorized() throws Exception {
        super.findEntities_noJwt_shouldReturnUnauthorized();
    }

    @Test
    void findTags_whenNoTags_shouldReturnEmptyList() throws Exception {
        super.findEntities_whenNoEntities_shouldReturnEmptyList();
    }

    @Test
    void findTags_shouldReturnAllTags() throws Exception {
        super.findEntities_shouldReturnAllEntities();

        mockMvc.perform(get(getBaseRoute())
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(6))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("PERSONAL", "WORK", "FINANCE", "TRAINING", "EDUCATION", "FAMILY")));
    }
}
