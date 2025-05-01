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

    private static final String TEST_EMAIL = "testuser@example.com";
    private static final String TEST_PASSWORD = "1Password";

    @Bean
    public TestAuthClient testAuthClient(MockMvc mockMvc) throws Exception {
        return new TestAuthClient(mockMvc);
    }

    public static class TestAuthClient {

        private final MockMvc mockMvc;
        private final ObjectMapper mapper = new ObjectMapper();
        private String token;

        public TestAuthClient(MockMvc mockMvc) throws Exception {
            this.mockMvc = mockMvc;
            registerAndLogin();
        }

        private void registerAndLogin() throws Exception {
            try {
                var register = new UserRegistrationRequestRecord("testuser", "testuser", TEST_EMAIL, TEST_PASSWORD);

                mockMvc.perform(post("/api/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(register)))
                        .andReturn();
                // If user already exists, we can ignore errors here.
            } catch (Exception ignored) {
            }

            var login = new UserLoginRequestRecord(TEST_EMAIL, TEST_PASSWORD);
            String loginJson = mockMvc.perform(post("/api/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(login)))
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            this.token = mapper.readTree(loginJson).get("token").asText();
        }

        public String getJwtToken() {
            return token;
        }
    }
}