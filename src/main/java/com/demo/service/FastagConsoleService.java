package com.demo.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.model.FastagCustomer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RefreshScope
@Service
public class FastagConsoleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(FastagConsoleService.class);
	int counter = 0;

	@LoadBalanced
	@Bean
	private RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate;

	@HystrixCommand(groupKey = "FastagConsoleServiceGroup", commandKey = "getCustomerDetailsCommand", threadPoolKey = "FastagConsoleServiceKey", fallbackMethod = "getCustomerDetailsFallback", commandProperties = {
//			@HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
//			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
//			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000"),
//			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),

	})

	public Map getCustomerDetailsUsingHystrix(String fastagId) {
		LOGGER.info("getCustomerDetailsUsingHystrix Method::", FastagConsoleService.class);
		int counter = 0;
		try {
			FastagCustomer fastagCustomer = restTemplate
					.getForObject("http://fastagservice/fastag?fastagId=" + fastagId, FastagCustomer.class);
			Map<String, FastagCustomer> map = new HashMap<String, FastagCustomer>();
			map.put("customer", fastagCustomer);
			return map;
		} catch (RuntimeException e) {
			counter++;
			LOGGER.info("customer details Service Failed " + counter);
			throw new RuntimeException("Service is down");
		}
	}

	@Retryable(value = { RuntimeException.class }, maxAttempts = 5, backoff = @Backoff(delay = 10000))
	public Map getCustomerDetailsUsingRetryable(String fastagId) {
		LOGGER.info("getCustomerDetailsUsingRetryable Method::", FastagConsoleService.class);
		try {
			FastagCustomer fastagCustomer = restTemplate
					.getForObject("http://fastagservice/fastag?fastagId=" + fastagId, FastagCustomer.class);
			Map<String, FastagCustomer> map = new HashMap<String, FastagCustomer>();
			map.put("customer", fastagCustomer);
			counter++;
			return map;
		} catch (RuntimeException e) {
			counter++;
			LOGGER.info("fastag console Service Failed " + counter);
			throw new RuntimeException("Service is down");
		}
	}

	@Recover
	public Map recover(RuntimeException t) {
		LOGGER.info("recover Method::", FastagConsoleService.class);
		LOGGER.info("Service recovering from RuntimeException");
		Map<String, FastagCustomer> map = new HashMap<String, FastagCustomer>();
		return map;
	}

	public Map getCustomerDetailsFallback(String fastagId) {
		LOGGER.info("getCustomerDetailsFallback Method::", FastagConsoleService.class);
		FastagCustomer fastagCustomer = new FastagCustomer();
		fastagCustomer.setFastagId("0");
		Map<String, FastagCustomer> map = new HashMap<String, FastagCustomer>();
		map.put("customer", fastagCustomer);
		return map;
	}

}
