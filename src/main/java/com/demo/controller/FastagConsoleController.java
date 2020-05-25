package com.demo.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.service.FastagConsoleService;

@RestController
public class FastagConsoleController {

	private static final Logger LOGGER = LoggerFactory.getLogger(FastagConsoleController.class);
	static int counter = 0;

	@Autowired
	FastagConsoleService fastagConsoleService;

	@GetMapping(value = "/customerDetailsHystrix", params = { "fastagId" })
	public Map getFastagCustomerDetailsHystrix(@RequestParam String fastagId) {
		LOGGER.info("getCustomerDetailsUsingRetryable Method::", FastagConsoleController.class);
		return fastagConsoleService.getCustomerDetailsUsingHystrix(fastagId);

	}

	@GetMapping(value = "/customerDetailsRetryable", params = { "fastagId" })
	public Map getFastagCustomerDetailsRetryable(@RequestParam String fastagId) {
		LOGGER.info("getCustomerDetailsUsingRetryable Method::", FastagConsoleController.class);
		return fastagConsoleService.getCustomerDetailsUsingRetryable(fastagId);

	}

}
