package com.c4l.fileUploader.controller;

import static com.c4l.fileUploader.common.ApplicationConstant.EMPTY_FILE_ERROR_CODE;
import static com.c4l.fileUploader.common.ApplicationConstant.EMPTY_FILE_ERROR_DESC;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.c4l.fileUploader.common.ResponseUtil;
import com.c4l.fileUploader.exception.StorageException;
import com.c4l.fileUploader.model.GenericResponse;
import com.c4l.fileUploader.service.FileStreamingService;
import com.c4l.fileUploader.validation.ValidFile;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("V1/upload")
@Slf4j
public class FileUplodController {

	@Autowired
	private FileStreamingService fileStreamingService;
	
	@Bulkhead(name="uploadFile")
	@RateLimiter(name="uploadFile")
	@Validated
	@PostMapping
	public ResponseEntity<GenericResponse> uploadFile(@ValidFile @RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			log.error("uploadFile ::File is empty for the request");
			throw new StorageException(EMPTY_FILE_ERROR_CODE, EMPTY_FILE_ERROR_DESC, HttpStatus.BAD_REQUEST.value());
		}
		fileStreamingService.processUploadedFile(file);
		log.error("uploadFile ::Completed Processing file Stream");
		return ResponseUtil.response202(ResponseUtil.createGenericResponse());
	}
}
