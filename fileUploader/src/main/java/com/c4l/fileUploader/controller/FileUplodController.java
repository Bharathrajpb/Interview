package com.c4l.fileUploader.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.c4l.fileUploader.client.RewardServiceClient;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/upload")
@Slf4j
public class FileUplodController {
	
	private static int MAX_SIZE  = 1000;
	private static long count =1;
   
	@Autowired
	private RewardServiceClient rewardServiceClient;
	
	    @PostMapping
	    public ResponseEntity<String> uploadFile(
	    		@RequestParam("file") MultipartFile file) throws IOException {
	        
	           File testFile = new File("hello");
	           FileUtils.writeByteArrayToFile(testFile, file.getBytes());
	           List<String> listRewards=new ArrayList<String>();
	           LineIterator it = FileUtils.lineIterator(testFile, "UTF-8");
	        	    while (it.hasNext()) {
	        	        String line = it.nextLine();
	        	        listRewards.add(line);
	        	       if(count%MAX_SIZE==0) {
	        	    	   rewardServiceClient.processBulkrewards(listRewards);  
	        	    	   listRewards.clear();
	        	       }
	        	    }
	        	    if(!listRewards.isEmpty()) {
	        	    	 rewardServiceClient.processBulkrewards(listRewards);  
	        	    	   listRewards.clear();
	        	    }
				
	        return new ResponseEntity<String>("Done", HttpStatus.OK);
	    }
}
