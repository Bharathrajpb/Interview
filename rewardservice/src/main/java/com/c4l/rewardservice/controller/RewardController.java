package com.c4l.rewardservice.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c4l.rewardservice.common.ResponseUtil;
import com.c4l.rewardservice.entity.Rewards;
import com.c4l.rewardservice.exception.CardMissMatchException;
import com.c4l.rewardservice.model.GenericResponse;
import com.c4l.rewardservice.model.Reward;
import com.c4l.rewardservice.service.RewardService;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/rewards")
@RestController
@Slf4j
public class RewardController {

	@Autowired
	RewardService rewardService;
	
	
	@PostMapping("/bulkProcess")
	public  ResponseEntity<GenericResponse> processBulkrewards(@RequestBody List<String> rewardsList) {
		   log.info("Bulk processing started, we have"+rewardsList.size()+" recods to process");
		
		   rewardsList.parallelStream().forEach(record->{
			   try {
				   log.info("Printing for Deugging"+record); 
			   rewardService.processRewards(convertStringToPojo(record)) ;
			   }
			   catch(CardMissMatchException ex) {
				   //Silent Exit
			   }
			   catch(Exception ex) {
				   //can lag the data in failed Backup DB exit
				   log.error(ex.getMessage());
			   }
		   });
		   log.info("Bulk processing of records Ended");
	
		return ResponseUtil.response201(ResponseUtil.createGenericResponse(),"" );
		
	}
	
	
	@PostMapping
	public ResponseEntity<GenericResponse> insertRewards( @RequestBody Reward reward) {
		log.info("Entered checkCard input: {}", reward);
		
		//service call to process the rewards requests
		rewardService.processRewards(reward);
		
		return ResponseUtil.response201(ResponseUtil.createGenericResponse(),"" );
		
	}
	
  private Reward convertStringToPojo(String record) {
			Reward rew = new Reward();
			String [] recordArray=record.split("\\|");
			rew.setPseudoCard(recordArray[0]);
			rew.setBinNo(recordArray[1]);
			rew.setCardType(recordArray[2]);
			rew.setCifid(recordArray[3]);
			rew.setAmount((ResponseUtil.isNullOrEmpty(recordArray[4]))?BigDecimal.ZERO:new BigDecimal(recordArray[4]));
			rew.setPrimary((ResponseUtil.isNullOrEmpty(recordArray[5]))?false:Boolean.getBoolean(recordArray[5]));
			rew.setTranAmount(recordArray[6]);
			 log.info("Converted Pojo"+rew.toString());
			return rew;
	}	
}
