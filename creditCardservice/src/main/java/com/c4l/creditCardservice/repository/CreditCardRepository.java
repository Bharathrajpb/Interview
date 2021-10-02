package com.c4l.creditCardservice.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.c4l.creditCardservice.entity.CreditCard;

@Repository
public interface CreditCardRepository extends MongoRepository<CreditCard, String>{

	Optional<CreditCard> findByPseudoCard(String pseudoCard);
}
