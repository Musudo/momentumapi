package com.musadzeyt.momentumapi;

import com.musadzeyt.momentumapi.integration.AbstractIntegrationTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MomentumApplicationTests extends AbstractIntegrationTestContainer  {
	@Test
	void contextLoads() {
	}
}
