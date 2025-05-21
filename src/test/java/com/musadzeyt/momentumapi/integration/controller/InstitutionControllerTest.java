package com.musadzeyt.momentumapi.integration.controller;

import com.musadzeyt.momentumapi.dataFaker.generator.InstitutionGenerator;
import com.musadzeyt.momentumapi.domain.Institution;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import com.musadzeyt.momentumapi.repository.IInstitutionRepository;
import com.musadzeyt.momentumapi.service.entityService.InstitutionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class InstitutionControllerTest extends AbstractControllerTest<Institution> {
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;
    @Autowired
    private IInstitutionRepository institutionRepository;
    @Autowired
    private InstitutionGenerator institutionGenerator;
    @Autowired
    private InstitutionService institutionService;

    @Override
    protected String getBaseRoute() {
        return "/api/institutions";
    }

    @Override
    protected void deleteAllEntities() {
        institutionRepository.deleteAll();
    }

    @Override
    protected List<Institution> createSampleEntities() {
        return institutionGenerator.createInstitutions(3);
    }

    @Test
    void findInstitutions_noJwt_shouldReturnUnauthorized() throws Exception {
        super.findEntities_noJwt_shouldReturnUnauthorized();
    }

    @Test
    void findInstitutions_whenNoTags_shouldReturnEmptyList() throws Exception {
        super.findEntities_whenNoEntities_shouldReturnEmptyList();
    }

    @Test
    void findInstitutions_withValidJwt_shouldReturnAllEntities() throws Exception {
        super.findEntities_withValidJwt_shouldReturnAllEntities();

        mockMvc.perform(get(getBaseRoute())
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
//                .andExpect(jsonPath("$[*].name", containsInAnyOrder()))
                .andDo(result -> {
                    System.out.println("RESPONSE JSON: " + result.getResponse().getContentAsString());
                });
    }
}
