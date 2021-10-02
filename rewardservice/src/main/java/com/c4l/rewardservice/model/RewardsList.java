package com.c4l.rewardservice.model;

import java.util.List;

public class RewardsList {

	private List<Reward> rewards;

	public List<Reward> getRewards() {
		return rewards;
	}

	public void setRewards(List<Reward> rewards) {
		this.rewards = rewards;
	}

	@Override
	public String toString() {
		return "RewardsList [getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}
