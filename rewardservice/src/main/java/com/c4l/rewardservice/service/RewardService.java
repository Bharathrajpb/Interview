package com.c4l.rewardservice.service;

import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.model.Reward;

public interface RewardService {

	void processRewards(Reward reward);	
}
