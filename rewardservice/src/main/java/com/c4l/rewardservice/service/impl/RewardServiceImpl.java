package com.c4l.rewardservice.service.impl;

import static com.c4l.rewardservice.common.ApplicationConstant.NOCARD_FOUND_CODE;
import static com.c4l.rewardservice.common.ApplicationConstant.NOCARD_FOUND_DESC;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.c4l.rewardservice.client.CreditCardServiceClient;
import com.c4l.rewardservice.common.ApplicationConstant;
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
	RewardRepository rewardRepository;
	
	@Autowired
	private CreditCardServiceClient cardServiceProxy;
	 
	@Autowired
    private RewardsFailedReportRepository rewardFailureRepository;
	
	@Override
	public void processRewards(Reward reward) {
		 log.info("Entered into processRewards,Processing : {}",reward.toString());
		// check for valid credit card
		VerificationResponse verficationResponse = cardServiceProxy.checkCard(reward.getPseudoCard()).getBody();
		if (!VerificationResponse.Status.VERIFICATION_PASSED
				.equals(verficationResponse.status)) {
			 log.info("Card Not found -->"+reward.getPseudoCard());
			RewardsFailedReport rewardFailRep =prepareInputDataForFailure(reward);
			
			/* dumping the failed responses to RewardsFailedReport for tracking purpose. */
			 log.info("Inserting into Reward failed Report Table for{} ",rewardFailRep.toString());
			rewardFailureRepository.save(rewardFailRep);
			 log.info("After Data insertion CardNo {} Amount {}",reward.getPseudoCard(),reward.getAmount());

			//Exception if card not found
			//throw new CardMissMatchException(NOCARD_FOUND_CODE,NOCARD_FOUND_DESC,HttpStatus.NOT_FOUND.value());
			return;
		}
		// insert into DB
		// replace code with Map struct to replace pojos
		Rewards rew =prepareInputDataForRewards(reward);
		 log.info("Saving rewards to DB for PseudoCard----> {}  Amount-->{} "+reward.getPseudoCard(),reward.getAmount());
		rewardRepository.save(rew);
		
	}
	
	private Rewards prepareInputDataForRewards(Reward reward) {
		Rewards rew = new Rewards();
		rew.setAmount(reward.getAmount());
		rew.setBinNo(reward.getBinNo());
		rew.setCardType(rew.getCardType());
		rew.setCifid(reward.getCifid());
		rew.setPrimary(reward.isPrimary());
		rew.setPseudoCard(reward.getPseudoCard());
		rew.setTranAmount(reward.getTranAmount());
		return rew;

	}

	
	private RewardsFailedReport prepareInputDataForFailure(Reward reward) {
		RewardsFailedReport rewardFailRep = new RewardsFailedReport();
		rewardFailRep.setAmount(reward.getAmount());
		rewardFailRep.setBinNo(reward.getBinNo());
		rewardFailRep.setCardType(reward.getCardType());
		rewardFailRep.setCifid(reward.getCifid());
		rewardFailRep.setPrimary(reward.isPrimary());
		rewardFailRep.setPseudoCard(reward.getPseudoCard());
		rewardFailRep.setTranAmount(reward.getTranAmount());
		rewardFailRep.setDesc(ApplicationConstant.PSEUDOCARD_FAILURE_DESC);
		return rewardFailRep;
 
	}
}
