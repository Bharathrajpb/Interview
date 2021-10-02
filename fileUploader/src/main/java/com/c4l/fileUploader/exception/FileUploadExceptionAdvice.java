package com.c4l.fileUploader.exception;

import static com.c4l.fileUploader.common.ApplicationConstant.*;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.c4l.fileUploader.model.AppError;

@ControllerAdvice
public class FileUploadExceptionAdvice {

	@ExceptionHandler(IOException.class)
	public ResponseEntity<AppError> handleApplicationException(IOException ex) {
		return new ResponseEntity<>(new AppError(IO_EXCEPTION_ERROR_CODE,IO_EXCEPTION_ERROR_DESC),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<AppError> handleApplicationException(Exception ex) {
		return new ResponseEntity<>(new AppError(GENERIC_ERROR_CODE, ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
