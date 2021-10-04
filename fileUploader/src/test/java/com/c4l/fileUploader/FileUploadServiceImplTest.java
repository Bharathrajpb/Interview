package com.c4l.fileUploader;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.c4l.fileUploader.client.RewardServiceClient;
import com.c4l.fileUploader.model.GenericResponse;
import com.c4l.fileUploader.service.impl.FileUploadServiceImpl;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FileUploadServiceImplTest {

	
	@Mock
	private RewardServiceClient rewardServiceClient;


	@InjectMocks
	private FileUploadServiceImpl fileUploadServiceImpl = new FileUploadServiceImpl();
	
	
	@Test
	public void when_emptyList_called() {
		fileUploadServiceImpl.batchProcessRecords(new ArrayList());
		verify(rewardServiceClient,times(0)).processBulkrewards(anyList());
	}

	@Test
	public void when_null_called() {
		fileUploadServiceImpl.batchProcessRecords(null);
		verify(rewardServiceClient,times(0)).processBulkrewards(anyList());
	}
	
	@Test
	public void when_list_called() {
		ArrayList<String> list=new ArrayList<String>();
		list.add("dummy");
		fileUploadServiceImpl.batchProcessRecords(list);
		verify(rewardServiceClient,times(1)).processBulkrewards(anyList());
	}
	
}
