package com.c4l.fileUploader.controller;

import static com.c4l.fileUploader.common.ApplicationConstant.EMPTY_FILE_ERROR_CODE;
import static com.c4l.fileUploader.common.ApplicationConstant.EMPTY_FILE_ERROR_DESC;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
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
import com.c4l.fileUploader.service.FileUploadService;
import com.c4l.fileUploader.validation.ValidFile;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("V1/upload")
@Slf4j

public class FileUplodController {

	private static int MAX_CHUNK_SIZE = 1000;

	@Autowired
	private FileUploadService fileUploadService;
	
	@Validated
	@PostMapping
	public ResponseEntity<GenericResponse> uploadFile(@ValidFile @RequestParam("file") MultipartFile file) throws IOException {
		long count = 1;
		if (file.isEmpty()) {
			log.error("uploadFile ::File is empty for the request");
			throw new StorageException(EMPTY_FILE_ERROR_CODE, EMPTY_FILE_ERROR_DESC, HttpStatus.BAD_REQUEST.value());
		}
		InputStream inputStream = file.getInputStream();
		LineIterator it = IOUtils.lineIterator(inputStream, StandardCharsets.UTF_8.toString());
		List<String> listRewards = new ArrayList<String>();
		while (it.hasNext()) {
			String line = it.nextLine();
			listRewards.add(line);
			if (count % MAX_CHUNK_SIZE == 0) {
				fileUploadService.batchProcessRecords(List.copyOf(listRewards));
				listRewards.clear();
			}
			count++;
		}
		log.error("uploadFile ::All lines read came out,{} record pending for verification", listRewards.size());
		if (!listRewards.isEmpty()) {
			fileUploadService.batchProcessRecords(List.copyOf(listRewards));
			listRewards.clear();
		}
		log.error("uploadFile ::Completed Processing file Stream");
		return ResponseUtil.response202(ResponseUtil.createGenericResponse());
	}
}
