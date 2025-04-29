package com.musadzeyt.momentumapi.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musadzeyt.momentumapi.record.UserLoginRequestRecord;
import com.musadzeyt.momentumapi.record.UserRegistrationRequestRecord;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@TestConfiguration
public class TestUserProvider {

    @Bean
    public TestAuthClient testAuthClient(MockMvc mockMvc) {
        return new TestAuthClient(mockMvc);
    }

    public static class TestAuthClient {

        private final MockMvc mockMvc;
        private final ObjectMapper mapper = new ObjectMapper();
        private String token;

        public TestAuthClient(MockMvc mockMvc) {
            this.mockMvc = mockMvc;
        }

        public String getJwtToken() throws Exception {
            if (token == null) {
                registerAndLogin();
            }
            return token;
        }

        private void registerAndLogin() throws Exception {
            try {
                var register = new UserRegistrationRequestRecord("testuser", "testuser", "testuser@example.com", "1Password");

                mockMvc.perform(post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(register)))
                        .andReturn();
            } catch (Exception ignored) {
            }

            var login = new UserLoginRequestRecord("testuser@example.com", "1Password");
            String loginJson = mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(login)))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            this.token = mapper.readTree(loginJson).get("token").asText();
        }
    }
}

