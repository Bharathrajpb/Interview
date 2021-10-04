package com.c4l.fileUploader.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class GenericResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String statusCode;
	private String statusDesc;
	
	@Override
	public String toString() {
		return "GenericResponse [statusCode=" + statusCode + ", statusDesc=" + statusDesc + "]";
	}
}