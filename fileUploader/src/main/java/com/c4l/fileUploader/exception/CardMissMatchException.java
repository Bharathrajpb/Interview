package com.c4l.fileUploader.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class CardMissMatchException extends RuntimeException{

	private static final long serialVersionUIDAdder =1L;
	
	private final String errorType;
	private final String errorCode;
	private final String errorMessage;
	private String responseBody;
	private int statusCode=HttpStatus.BAD_REQUEST.value();

	
	public CardMissMatchException(String errorType, String errorCode, String errorMessage, String responseBody,
			int statusCode) {
		super();
		this.errorType = errorType;
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.responseBody = responseBody;
		this.statusCode = statusCode;
	}
	
	public CardMissMatchException(String errorCode, String errorMessage,int statusCode) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.statusCode = statusCode;
		this.errorType=null;
	}
	
	public CardMissMatchException(String errorCode, String errorMessage) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.errorType=null;
	}
}
