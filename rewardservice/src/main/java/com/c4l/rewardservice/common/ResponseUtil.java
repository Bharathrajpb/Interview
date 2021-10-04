package com.c4l.rewardservice.common;

import static com.c4l.rewardservice.common.ApplicationConstant.GENERIC_RESPONSE_CODE;
import static com.c4l.rewardservice.common.ApplicationConstant.GENERIC_RESPONSE_DESC;
import static com.c4l.rewardservice.common.ApplicationConstant.HEADER_PARAM_LOCATION;
import static com.c4l.rewardservice.common.ApplicationConstant.HEADER_PARAM_RESPONSE_TIME;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.c4l.rewardservice.model.GenericResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ResponseUtil {
	
	
	
	public static GenericResponse createGenericResponse() {
		GenericResponse response=new GenericResponse();
		response.setStatusCode(GENERIC_RESPONSE_CODE);
		response.setStatusDesc(GENERIC_RESPONSE_DESC);
		return response;
	}
	
	public static <T> ResponseEntity<T> response201(T object,String pathParam){
		HttpHeaders responseHeaders=generateResponseHeader();
		
		try {
			URI uri=new URI(ServletUriComponentsBuilder.fromCurrentRequest().path(null).buildAndExpand(object).toUri().getPath());
			responseHeaders.set(HEADER_PARAM_LOCATION, uri.toString());
		}
		catch (URISyntaxException e) {
			log.debug("Exception"+e.getMessage());
		}
		return new ResponseEntity<T>(responseHeaders,HttpStatus.CREATED);
	}
	
	public static <T> ResponseEntity<T> response202(T object){
		HttpHeaders responseHeaders=generateResponseHeader();
		return new ResponseEntity<T>(object,responseHeaders,HttpStatus.ACCEPTED);
	}
	

	public static <T> ResponseEntity<T> response200(T object){
		HttpHeaders responseHeaders=generateResponseHeader();
		
		
		return new ResponseEntity<T>(object,responseHeaders,HttpStatus.OK);
	}
	
	

	public static HttpHeaders generateResponseHeader() {
		HttpHeaders responseHeaders=new HttpHeaders();
		responseHeaders.set(HEADER_PARAM_RESPONSE_TIME, String.valueOf(System.currentTimeMillis()));
		
		return responseHeaders;
	}
	
	
	public static boolean isNullOrEmpty(String value) {
		if(value==null || value.isEmpty()) {
			return true;
		}
		return false;
	}
	
	
	
}
