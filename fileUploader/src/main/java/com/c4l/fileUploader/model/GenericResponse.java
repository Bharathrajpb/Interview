package com.c4l.fileUploader.model;

import lombok.Data;

@Data
public class GenericResponse {

	private String statusCode;
	private String statusDesc;
	
	@Override
	public String toString() {
		return "GenericResponse [statusCode=" + statusCode + ", statusDesc=" + statusDesc + "]";
	}
}