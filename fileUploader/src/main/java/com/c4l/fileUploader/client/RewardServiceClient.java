package com.c4l.fileUploader.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.c4l.fileUploader.model.GenericResponse;


@RequestMapping("V1/rewards")
@FeignClient(name="rewardservice")
public interface RewardServiceClient {

	@GetMapping( "/bulkProcess")
	public  ResponseEntity<GenericResponse> processBulkrewards(@RequestBody List<String> rewardsList);
	
}