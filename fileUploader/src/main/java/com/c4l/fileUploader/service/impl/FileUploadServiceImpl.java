package com.c4l.fileUploader.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.c4l.fileUploader.client.RewardServiceClient;
import com.c4l.fileUploader.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileUploadServiceImpl implements  FileUploadService{

	@Autowired
	private RewardServiceClient rewardServiceClient;
	
	
	@Async("fileUploadExecutor")
	public void batchProcessRecords(List<String> rewardRecordList) {
		log.info("Async file execution batchProcessRecords started by thread {}",Thread.currentThread().getName());
		//call reward service batch insert client to process the client
		if(null!=rewardRecordList&&!rewardRecordList.isEmpty())
		rewardServiceClient.processBulkrewards(rewardRecordList);
		log.info("batchProcessRecords execution completed by thread {}",Thread.currentThread().getName());

	}
	
	
}
