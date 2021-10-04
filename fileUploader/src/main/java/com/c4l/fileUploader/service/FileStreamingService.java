package com.c4l.fileUploader.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface FileStreamingService {

	void processUploadedFile(MultipartFile file) throws IOException;
}
