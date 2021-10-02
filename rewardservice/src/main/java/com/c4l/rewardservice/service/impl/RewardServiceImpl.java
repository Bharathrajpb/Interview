package com.c4l.rewardservice.service.impl;

import static com.c4l.rewardservice.common.ApplicationConstant.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.c4l.rewardservice.client.CreditCardServiceClient;
import com.c4l.rewardservice.common.ObjectMapperUtil;
import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.entity.RewardsFailedReport;
import com.c4l.rewardservice.exception.CardMissMatchException;
import com.c4l.rewardservice.model.Reward;
import com.c4l.rewardservice.model.VerificationResponse;
import com.c4l.rewardservice.repository.RewardRepository;
import com.c4l.rewardservice.repository.RewardsFailedReportRepository;
import com.c4l.rewardservice.service.RewardService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RewardServiceImpl implements RewardService {

	@Autowired
	private RewardRepository rewardRepository;

	@Autowired
	private CreditCardServiceClient cardServiceProxy;

	@Autowired
	private RewardsFailedReportRepository rewardFailureRepository;

	@Override
	public void processRewards(Reward reward) {
		log.info("Entered into processRewards,Processing : {}", reward.toString());
		// check for valid credit card
		VerificationResponse verficationResponse = cardServiceProxy.checkCard(reward.getPseudoCard()).getBody();
		if (!VerificationResponse.Status.VERIFICATION_PASSED.equals(verficationResponse.status)) {
			log.info("Card Not found Throwing Exception" + reward.getPseudoCard());

			// Exception if card not found
			throw new CardMissMatchException(NOCARD_FOUND_CODE, NOCARD_FOUND_DESC, HttpStatus.NOT_FOUND.value());
		}
		// insert into DB
		// replace code with Map struct to replace pojos
		Rewards rew = ObjectMapperUtil.prepareInputDataForRewards(reward);
		log.info("Saving rewards to DB for PseudoCard----> {}  Amount-->{} " + reward.getPseudoCard(),
				reward.getAmount());
		rewardRepository.save(rew);

	}

	@Async("rewardServiceExecutor")
	public void processBatchRewards(List<String> rewardsList) {
		log.info("Entered processBatchRewards Async Executor ,Thread {}" , Thread.currentThread().getName());

		rewardsList.parallelStream().forEach(record -> {
			try {
				processRewards(ObjectMapperUtil.convertStringToPojo(record));
			} catch (CardMissMatchException ex) {
				RewardsFailedReport rewardFailRep = ObjectMapperUtil.prepareInputDataForFailure(record,
						NOCARD_FOUND_DESC);
				/* dumping the failed responses to RewardsFailedReport for tracking purpose. */
				log.info("Inserting into Reward failed Report Table for{} ", rewardFailRep.toString());
				rewardFailureRepository.save(rewardFailRep);
			} catch (Exception ex) {
				RewardsFailedReport rewardFailRep = ObjectMapperUtil.prepareInputDataForFailure(record, TECH_ERROR);
				/* dumping the failed responses also logging the Exception. */
				log.info("Inserting into Reward failed Report Table for{} ", rewardFailRep.toString());
				rewardFailureRepository.save(rewardFailRep);
				log.error(ex.getMessage());
			}
		});

	}

}
