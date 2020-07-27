package com.example.microservices.limitservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservices.limitservice.bean.Configuration;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@RestController
public class LimitsConfigurationController {
	@Autowired
	private Configuration configuration;
	
	@GetMapping("/limits")
	@HystrixCommand(fallbackMethod = "fallbackRetrivelLimitsFromConfigurations", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000")
			})
	public LimitConfiguration retrivelLimitsFromConfigurations() throws InterruptedException {
		//Thread.sleep(1000);
		return new LimitConfiguration(configuration.getMinimum(),configuration.getMaximum());
	}
	
	public LimitConfiguration fallbackRetrivelLimitsFromConfigurations() {
		return new LimitConfiguration(8,999);
	}

}
