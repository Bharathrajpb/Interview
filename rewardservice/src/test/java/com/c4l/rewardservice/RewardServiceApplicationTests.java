package com.c4l.rewardservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(properties = "spring.cloud.config.enabled=false")
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
class RewardServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
