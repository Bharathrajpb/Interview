package com.c4l.rewardservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c4l.rewardservice.common.ResponseUtil;
import com.c4l.rewardservice.model.GenericResponse;
import com.c4l.rewardservice.model.Reward;
import com.c4l.rewardservice.service.RewardService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("V1/rewards")
@RestController
@Slf4j
public class RewardController {

	@Autowired
	private RewardService rewardService;

	@PostMapping("/bulkProcess")
	public ResponseEntity<GenericResponse> processBulkrewards(@RequestBody List<String> rewardsList) {
		log.info("processBulkrewards--> Delegating batch processing rewards to Async Service {} recods to process",
				rewardsList.size());
		rewardService.processBatchRewards(rewardsList);
		log.info("processBulkrewards--> completed delagation to Async service");
		return ResponseUtil.response202(ResponseUtil.createGenericResponse());
	}

	@PostMapping
	public ResponseEntity<GenericResponse> insertRewards(@RequestBody Reward reward) {
		log.info("Entered checkCard input: {}", reward);

		// service call to process the rewards requests
		rewardService.processRewards(reward);

		return ResponseUtil.response201(ResponseUtil.createGenericResponse(), "");

	}

}
