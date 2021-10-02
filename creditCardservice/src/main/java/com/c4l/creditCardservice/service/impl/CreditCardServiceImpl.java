package com.c4l.creditCardservice.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.c4l.creditCardservice.entity.CreditCard;
import com.c4l.creditCardservice.model.VerificationResponse;
import com.c4l.creditCardservice.repository.CreditCardRepository;
import com.c4l.creditCardservice.service.CreditCardService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CreditCardServiceImpl implements CreditCardService {

	@Autowired
	CreditCardRepository creditCardRepository; 
	
	@Override
	public VerificationResponse checkCard(String pseudoCard) {
		log.info("Entered checkCard service for checking cerdit card: {}", pseudoCard);
		Optional<CreditCard> card=creditCardRepository.findByPseudoCard(pseudoCard);
		return card.isPresent()?VerificationResponse.passed(pseudoCard):VerificationResponse.failed(pseudoCard);
	}

}
