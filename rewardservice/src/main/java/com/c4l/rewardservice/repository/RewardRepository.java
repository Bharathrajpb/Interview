package com.c4l.rewardservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.c4l.rewardservice.entity.Rewards;

@Repository
public interface RewardRepository extends MongoRepository<Rewards, String> {

}
