package com.c4l.fileUploader.service.impl;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileUploadServiceImpl {


	@Async("fileUploadExecutor")
	public void batchProcessRecords(List<String> rewardRecordList) {
		
		//call reward service batch insert client to process the client
		
	}
	
	
}
