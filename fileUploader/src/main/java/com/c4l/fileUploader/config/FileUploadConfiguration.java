package com.c4l.fileUploader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import brave.sampler.Sampler;

@Configuration
@EnableAsync
public class FileUploadConfiguration {

	
	 @Bean(name = "multipartResolver")
	    public CommonsMultipartResolver multipartResolver() {
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	        multipartResolver.setMaxUploadSize(-1);
	        return multipartResolver;
	    }
	 
	  @Bean(name = "fileUploadExecutor")
	    public TaskExecutor fileUploadExecutor() {
	        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.initialize();
	        return executor;
	    }
	 
		@Bean
		  public Sampler defaultSampler(){
		    return Sampler.ALWAYS_SAMPLE;
		  }
}
