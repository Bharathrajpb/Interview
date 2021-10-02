package com.c4l.fileUploader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("com.c4l.fileUploader")
public class FileUploaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(FileUploaderApplication.class, args);
	}

	
	
}
