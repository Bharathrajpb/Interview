package com.c4l.rewardservice.service;

import java.util.List;

import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.model.Reward;

public interface RewardService {

	void processRewards(Reward reward);	
	void processBatchRewards(List<String> rewardsList);
}
