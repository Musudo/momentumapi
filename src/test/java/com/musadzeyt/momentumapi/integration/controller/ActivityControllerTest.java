package com.musadzeyt.momentumapi.integration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musadzeyt.momentumapi.dataFaker.DataSeeder;
import com.musadzeyt.momentumapi.dataFaker.generator.ActivityGenerator;
import com.musadzeyt.momentumapi.domain.Activity;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.ActivityDto;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import com.musadzeyt.momentumapi.util.mapper.ActivityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ActivityControllerTest extends AbstractControllerTest<ActivityDto> {
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;
    @Autowired
    private ActivityGenerator activityGenerator;
    @Autowired
    private DataSeeder dataSeeder;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected String getBaseRoute() {
        return "/api/v1/activities";
    }

    @Override
    protected void eraseData() {
        dataSeeder.eraseData();
    }

    @Override
    protected void seedTestData() {
        dataSeeder.seedData();
    }

    @Override
    protected ActivityDto createSampleEntity() throws Exception {
        Activity activity = activityGenerator.createActivitiesToday(1).getFirst();

        MvcResult result = mockMvc.perform(post(getBaseRoute())
                        .header("Authorization", "Bearer " + authClient.getJwtToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ActivityMapper.INSTANCE.entityToDto(activity))))
                .andExpect(status().isOk())
                .andReturn();

        return objectMapper.readValue(result.getResponse().getContentAsString(), ActivityDto.class);
    }

    @Test
    void findActivities_noJwt_shouldReturnUnauthorized() throws Exception {
        super.findEntities_noJwt_shouldReturnUnauthorized();
    }

    @Test
    void findActivities_whenNoActivities_shouldReturnEmptyList() throws Exception {
        super.findEntities_whenNoEntities_shouldReturnEmptyList();
    }

    @Test
    void findActivities_shouldReturnAllActivities() throws Exception {
        super.findEntities_shouldReturnAllEntities();

        // TODO: work out this further later
    }
}
