package com.c4l.fileUploader.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.c4l.fileUploader.service.FileStreamingService;
import com.c4l.fileUploader.service.FileUploadService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FileStreamingServiceImpl implements FileStreamingService{

	
	private  static int MAX_CHUNK_SIZE;

	@Autowired
	private FileUploadService fileUploadService;
	
	@Override
	public void processUploadedFile(MultipartFile file)throws IOException {
		long count = 1;
		log.info("processing the file started");
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
		
	}
	
	   @Value("${file.max.buffer.size:1000}")
	    public void setMaxChunckSize(int size){
		 FileStreamingServiceImpl.MAX_CHUNK_SIZE = size;
	    }
    
}
