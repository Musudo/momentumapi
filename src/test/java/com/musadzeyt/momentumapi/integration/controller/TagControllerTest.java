package com.musadzeyt.momentumapi.integration.controller;

import com.musadzeyt.momentumapi.domain.Tag;
import com.musadzeyt.momentumapi.enums.TagNameEnum;
import com.musadzeyt.momentumapi.faker.factory.TagFactory;
import com.musadzeyt.momentumapi.integration.AbstractIntegrationTestContainer;
import com.musadzeyt.momentumapi.integration.TestUserProvider;
import com.musadzeyt.momentumapi.repository.ITagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class TagControllerTest extends AbstractIntegrationTestContainer {
    @Autowired
    protected TestUserProvider.TestAuthClient authClient;
    @Autowired
    private ITagRepository tagRepository;
    @Autowired
    private TagFactory tagFactory;

    @BeforeEach
    void setUp() throws Exception {
        tagRepository.deleteAll();
    }

    @Test
    void findTags_noJwt_shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void findTags_whenNoTags_shouldReturnEmptyList() throws Exception {
        mockMvc.perform(get("/api/tags")
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    void findTags_withValidJwt_shouldReturnAllTags() throws Exception {
        Tag educationTag = tagFactory.create(TagNameEnum.EDUCATION);
        tagRepository.save(educationTag);

        Tag workTag = tagFactory.create(TagNameEnum.WORK);
        tagRepository.save(workTag);

        Tag familyTag = tagFactory.create(TagNameEnum.FAMILY);
        tagRepository.save(familyTag);

        mockMvc.perform(get("/api/tags")
                        .header("Authorization", "Bearer " + authClient.getJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].name", org.hamcrest.Matchers.containsInAnyOrder(
                        TagNameEnum.EDUCATION.name(),
                        TagNameEnum.WORK.name(),
                        TagNameEnum.FAMILY.name()
                )));
    }
}
