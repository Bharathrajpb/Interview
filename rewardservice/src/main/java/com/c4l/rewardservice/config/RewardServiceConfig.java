package com.c4l.rewardservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class RewardServiceConfig {

	@Value("${task.executor.corePoolSize:20}")
	private Integer  corePoolSize;
	
	@Value("${task.executor.maxPoolSize:20}")
	private Integer  maxPoolSize;
	
	 @Bean(name = "rewardServiceExecutor")
	    public TaskExecutor fileUploadExecutor() {
	        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	        executor.setCorePoolSize(corePoolSize);
	        executor.setThreadNamePrefix("Async-Rewards-");
	        executor.setMaxPoolSize(maxPoolSize);
	        executor.initialize();
	        return executor;
	    }

}
