package com.c4l.rewardservice;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.validation.Validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.model.GenericResponse;
import com.c4l.rewardservice.model.Reward;
import com.c4l.rewardservice.service.RewardService;

import antlr.collections.List;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
@SpringBootTest(properties = "spring.cloud.config.enabled=false", webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@ActiveProfiles("test")
public class RewardControllerTest {

	MockMvc mockMvc;

	@Mock
	private com.c4l.rewardservice.controller.RewardController rewardController;

	@MockBean
	private RewardService rewardService;

	@Autowired
	private TestRestTemplate template;
	
	@LocalServerPort
	private int port;

	
	
	@BeforeEach
	public void setup() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(rewardController).build();
	}

	@Test
	public void testRewardShouldBeRegistered() throws Exception {
		String pseudoCardd = "CCE-fjhfirghhth";
		Rewards rewards = new Rewards();
		rewards.setPseudoCard(pseudoCardd);
		rewards.setAmount(BigDecimal.TEN);
		doNothing().when(rewardService).processRewards(any(Reward.class));
		Reward reward = new Reward();
		reward.setPseudoCard(pseudoCardd);
		reward.setAmount(BigDecimal.TEN);
		ResponseEntity<GenericResponse> response = template.postForEntity("http://localhost:"+port+"/V1/rewards", getHttpEntity(reward), GenericResponse.class);
		assertThat(201, is(response.getStatusCode().value()));
	}
	
	@Test
	public void testBulkRewardsShouldBeRegistered() throws Exception {

		ArrayList<String> listRewards = new ArrayList<String>();
		listRewards.add("1");
		listRewards.add("2");
		doNothing().when(rewardService).processBatchRewards(any(ArrayList.class));

		ResponseEntity<GenericResponse> response = template.postForEntity(
				"http://localhost:" + port + "/V1/rewards/bulkProcess", getHttpEntity(listRewards),
				GenericResponse.class);
		assertThat(202, is(response.getStatusCode().value()));
	}
	
	private HttpEntity<Object> getHttpEntity(Object body) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new HttpEntity<Object>(body, headers);
	}
}