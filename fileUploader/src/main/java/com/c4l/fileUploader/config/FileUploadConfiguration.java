package com.c4l.fileUploader.config;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${task.executor.corePoolSize:20}")
	private int corePoolSize;
	
	@Value("${task.executor.maxPoolSize:20}")
	private int maxPoolSize;
	
	 @Bean(name = "multipartResolver")
	    public CommonsMultipartResolver multipartResolver() {
	        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	        multipartResolver.setMaxUploadSize(-1);
	        return multipartResolver;
	    }
	 
	  @Bean(name = "fileUploadExecutor")
	    public TaskExecutor fileUploadExecutor() {
	        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setCorePoolSize(corePoolSize);
	        executor.setThreadNamePrefix("Async-File-");
	        executor.setMaxPoolSize(maxPoolSize);
	        executor.initialize();
	        return executor;
	    }
	 
		@Bean
		  public Sampler defaultSampler(){
		    return Sampler.ALWAYS_SAMPLE;
		  }
}
