package com.c4l.rewardservice.common;

import java.math.BigDecimal;

import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.entity.RewardsFailedReport;
import com.c4l.rewardservice.model.Reward;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ObjectMapperUtil {

	public static Rewards prepareInputDataForRewards(Reward reward) {
		Rewards rew = new Rewards();
		rew.setAmount(reward.getAmount());
		rew.setBinNo(reward.getBinNo());
		rew.setCardType(rew.getCardType());
		rew.setCifid(reward.getCifid());
		rew.setPrimary(reward.isPrimary());
		rew.setPseudoCard(reward.getPseudoCard());
		rew.setPonits(reward.getPonits());
		return rew;

	}

	public static RewardsFailedReport prepareInputDataForFailure(String record,String desc) {
		RewardsFailedReport rewardFailRep = new RewardsFailedReport();
		rewardFailRep.setRecord(record);
		rewardFailRep.setDesc(desc);
		return rewardFailRep;

	}

	public static Reward convertStringToPojo(String record) {
		Reward rew = new Reward();
		String[] recordArray = record.split("\\|");
		rew.setPseudoCard(recordArray[0]);
		rew.setBinNo(recordArray[1]);
		rew.setCardType(recordArray[2]);
		rew.setCifid(recordArray[3]);
		rew.setAmount((ResponseUtil.isNullOrEmpty(recordArray[4])) ? BigDecimal.ZERO : new BigDecimal(recordArray[4]));
		rew.setPrimary((ResponseUtil.isNullOrEmpty(recordArray[5])) ? false : Boolean.getBoolean(recordArray[5]));
		rew.setPonits(recordArray[6]);
		log.info("Converted Pojo" + rew.toString());
		return rew;
	}

	/*
	 * public static RewardsFailedReport prepareInputDataForFailure(Reward reward) {
	 * RewardsFailedReport rewardFailRep = new RewardsFailedReport();
	 * rewardFailRep.setAmount(reward.getAmount());
	 * rewardFailRep.setBinNo(reward.getBinNo());
	 * rewardFailRep.setCardType(reward.getCardType());
	 * rewardFailRep.setCifid(reward.getCifid());
	 * rewardFailRep.setPrimary(reward.isPrimary());
	 * rewardFailRep.setPseudoCard(reward.getPseudoCard());
	 * rewardFailRep.setTranAmount(reward.getTranAmount());
	 * rewardFailRep.setDesc(ApplicationConstant.PSEUDOCARD_FAILURE_DESC); return
	 * rewardFailRep;
	 * 
	 * }
	 */

}
