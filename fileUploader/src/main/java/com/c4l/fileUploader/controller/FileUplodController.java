package com.c4l.fileUploader.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.c4l.fileUploader.common.ResponseUtil;
import com.c4l.fileUploader.model.GenericResponse;
import com.c4l.fileUploader.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/upload")
@Slf4j
public class FileUplodController {

	private static int MAX_SIZE = 1000;
	

	@Autowired
	private FileUploadService fileUploadService;

	@PostMapping
	public  ResponseEntity<GenericResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
		 long count = 1;
		//boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		/*
		 * DiskFileItemFactory factory = new DiskFileItemFactory();
		 * factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		 * factory.setSizeThreshold(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD * 2);
		 * FileCleaningTracker fileCleaningTracker = FileCleanerCleanup
		 * .getFileCleaningTracker(request.getSession().getServletContext());
		 * factory.setFileCleaningTracker(fileCleaningTracker); // Configure a
		 * repository (to ensure a secure temp location is used) ServletFileUpload
		 * upload = new ServletFileUpload(factory);
		 * 
		 * 
		 * LineIterator it = upload.getFileItemFactory().
		 */
		
		
		File testFile = new File("hello");
		FileUtils.writeByteArrayToFile(testFile, file.getBytes());
		List<String> listRewards = new ArrayList<String>();
		LineIterator it = FileUtils.lineIterator(testFile);
		while (it.hasNext()) {
			String line = it.nextLine();
			listRewards.add(line);
			if (count % MAX_SIZE == 0) {
				fileUploadService.batchProcessRecords(List.copyOf(listRewards));
				listRewards.clear();
			}
			count++;
		}
		if (!listRewards.isEmpty()) {
			fileUploadService.batchProcessRecords(List.copyOf(listRewards));
			listRewards.clear();
		}

		return ResponseUtil.response202(ResponseUtil.createGenericResponse());
	}
}
