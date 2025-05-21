package com.musadzeyt.momentumapi;

import com.musadzeyt.momentumapi.integration.AbstractTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class MomentumApplicationTests extends AbstractTestContainer {
	@Test
	void contextLoads() {
	}
}
