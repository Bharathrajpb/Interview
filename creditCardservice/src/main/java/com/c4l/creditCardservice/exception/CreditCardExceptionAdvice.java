package com.c4l.creditCardservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.c4l.creditCardservice.model.AppError;
import com.mongodb.MongoSocketOpenException;

@ControllerAdvice
public class CreditCardExceptionAdvice {
	
	
	/*
	 * @ExceptionHandler(MongoSocketOpenException.class) public
	 * ResponseEntity<AppError> handleApplicationException (MongoSocketOpenException
	 * ex){ return new ResponseEntity<>(new
	 * AppError(String.valueOf(ex.getCode()),ex.getMessage()),HttpStatus.
	 * GATEWAY_TIMEOUT); }
	 */
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<AppError> handleApplicationException (Exception ex){
		return new ResponseEntity<>(new AppError("010",ex.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
}
