package com.c4l.creditCardservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.c4l.creditCardservice.common.ResponseUtil;
import com.c4l.creditCardservice.model.VerificationResponse;
import com.c4l.creditCardservice.service.CreditCardService;

import lombok.extern.slf4j.Slf4j;


@RequestMapping("/creditCard")
@RestController
@Slf4j
public class CreditCardController {

	@Autowired
	CreditCardService creditCardService;
	
	
	//@CircuitBreaker(name = "default", fallbackMethod = "checkCardFallBack")
	@GetMapping( "/checkCard/{pseudoCard}")
	public ResponseEntity<VerificationResponse> checkCard(@PathVariable String pseudoCard) {
		log.info("Entered checkCard input: {}", pseudoCard);
		
		//Verify the given pseudo card against the data base
		VerificationResponse reponse =creditCardService.checkCard(pseudoCard);
		log.info("Exiting checkCard {}", pseudoCard);
		return ResponseUtil.response200(reponse);
	}	
  	
	/*
	 * public ResponseEntity<VerificationResponse> checkCardFallBack(Exception ex){
	 * 
	 * }
	 */
	
}
