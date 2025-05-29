package com.musadzeyt.momentumapi.integration.controller;

import com.musadzeyt.momentumapi.dataFaker.DataSeeder;
import com.musadzeyt.momentumapi.api.v1.dto.entityDto.TaskDto;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskControllerTest extends AbstractControllerTest<TaskDto> {
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;
    @Autowired
    private DataSeeder dataSeeder;

    @Override
    protected String getBaseRoute() {
        return "/api/v1/tasks";
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
    protected TaskDto createSampleEntity() throws Exception {
        return null;
    }

    @Test
    void findTasks_noJwt_shouldReturnUnauthorized() throws Exception {
        super.findEntities_noJwt_shouldReturnUnauthorized();
    }

    @Test
    void findTasks_whenNoTasks_shouldReturnEmptyList() throws Exception {
        super.findEntities_whenNoEntities_shouldReturnEmptyList();
    }

    @Test
    void findTasks_shouldReturnAllTasks() throws Exception {
        super.findEntities_shouldReturnAllEntities();

        // TODO: work out this further later
    }
}
