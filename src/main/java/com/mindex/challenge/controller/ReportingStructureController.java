package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;

@RestController
public class ReportingStructureController {
	
	private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureController.class); 
	
	@Autowired
	private ReportingStructureService reportingStructureService;
	
	@Autowired
	Environment environment;
	
	@GetMapping("/reporting-structure/{id}")
	private ReportingStructure read(@PathVariable String id) {
		LOG.debug("Received Reporting Structure create request for employee id [{}]", id);
		
		return reportingStructureService.read(id,environment.getProperty("local.server.port"));
		
	}
}
