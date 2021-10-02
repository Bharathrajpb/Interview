package com.c4l.rewardservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.c4l.rewardservice.entity.RewardsFailedReport;

@Repository
public interface RewardsFailedReportRepository extends MongoRepository<RewardsFailedReport, String> {

}
