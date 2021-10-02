package com.c4l.rewardservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import static com.c4l.rewardservice.common.ApplicationConstant.*;
import com.c4l.rewardservice.model.AppError;

@ControllerAdvice
public class RewardsExceptionAdvice {

	@ExceptionHandler(CardMissMatchException.class)
	public ResponseEntity<AppError> handleApplicationException(CardMissMatchException ex) {
		return new ResponseEntity<>(new AppError(ex.getErrorCode(), ex.getErrorMessage()),
				HttpStatus.valueOf(ex.getStatusCode()));
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<AppError> handleApplicationException(Exception ex) {
		return new ResponseEntity<>(new AppError(GENERIC_ERROR_CODE, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
