package com.c4l.rewardservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.repository.RewardRepository;

@DataMongoTest(properties = "spring.cloud.config.enabled=false")
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations="classpath:application-test.properties")
@ActiveProfiles("test")
public class MongoDbSpringIntegrationTest {

	@Autowired
	RewardRepository rewardRepository;

	@Test
	public void saveReward() {
		String pseudoCard = "CCE-dfjbjfbdjfkgdf";
		Rewards rewards = new Rewards();
		rewards.setPseudoCard(pseudoCard);
		rewards.setAmount(BigDecimal.TEN);
		rewardRepository.save(rewards);
		List<Rewards> rewardsList = rewardRepository.findAll();
		assertThat(rewardsList).hasSize(1).extracting("pseudoCard").contains(pseudoCard);

	}
}