package com.c4l.rewardservice;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.c4l.rewardservice.client.CreditCardServiceClient;
import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.exception.CardMissMatchException;
import com.c4l.rewardservice.model.Reward;
import com.c4l.rewardservice.model.VerificationResponse;
import com.c4l.rewardservice.repository.RewardRepository;
import com.c4l.rewardservice.repository.RewardsFailedReportRepository;
import com.c4l.rewardservice.service.RewardService;
import com.c4l.rewardservice.service.impl.RewardServiceImpl;
import com.mongodb.connection.Stream;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class RewardServiceImpTest {

	@Mock
	RewardRepository rewardRepository;

	@Mock
	private CreditCardServiceClient cardServiceProxy;

	@Mock
	private RewardsFailedReportRepository rewardFailureRepository;

	@InjectMocks
	private RewardService rewardService = new RewardServiceImpl();
	
	@Mock
	private Validator validator;
	
	

	@Test
	public void when_valid_card() {

		String pseudoCard = "CCE-q2ejhfjkgd";
		Set<ConstraintViolation<Reward>> violations =new HashSet<ConstraintViolation<Reward>>();
		Rewards reward = new Rewards();
		ResponseEntity<VerificationResponse> verificationSuccess = new ResponseEntity<VerificationResponse>(
				VerificationResponse.passed(pseudoCard), HttpStatus.OK);
		when(validator.validate(new Reward())).thenReturn(violations);
		when(cardServiceProxy.checkCard(pseudoCard)).thenReturn(verificationSuccess);
		when(rewardRepository.save(any(Rewards.class))).thenReturn(reward);
		// test
		Reward rewardRequest = new Reward();
		rewardRequest.setPseudoCard(pseudoCard);
		rewardRequest.setCifid("12345678");
		rewardRequest.setPonits("100");
		rewardRequest.setBinNo("12345678");
		rewardRequest.setAmount(BigDecimal.ONE);
	    rewardService.processRewards(rewardRequest);

	}
	
	@Test
	public void when_In_valid_card() {
		Assertions.assertThrows(CardMissMatchException.class, () -> {
			String failedCard = "CCE-q2ejhfjkgdgf";
			Set<ConstraintViolation<Reward>> violations = new HashSet<ConstraintViolation<Reward>>();
			ResponseEntity<VerificationResponse> verificationFailed = new ResponseEntity<VerificationResponse>(
					VerificationResponse.failed(failedCard), HttpStatus.OK);
			Rewards reward = new Rewards();
			when(validator.validate(new Reward())).thenReturn(violations);
			when(cardServiceProxy.checkCard(failedCard)).thenReturn(verificationFailed);
			when(rewardRepository.save(any(Rewards.class))).thenReturn(reward);
			Reward rewardRequest = new Reward();
			rewardRequest.setPseudoCard(failedCard);
			rewardService.processRewards(rewardRequest);
		});

	}
	
		
}