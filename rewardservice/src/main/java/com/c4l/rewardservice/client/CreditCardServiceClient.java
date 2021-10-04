package com.c4l.rewardservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.c4l.rewardservice.model.VerificationResponse;
@RequestMapping("V1/creditCard")
@FeignClient(name="creditCardservice")
public interface CreditCardServiceClient {

	@GetMapping( "/checkCard/{pseudoCard}")
	public ResponseEntity<VerificationResponse> checkCard(@PathVariable String pseudoCard);
	
}
