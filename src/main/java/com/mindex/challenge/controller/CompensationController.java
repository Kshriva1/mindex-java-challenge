package com.mindex.challenge.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.CompensationParts;
import com.mindex.challenge.service.CompensationService;

@RestController
public class CompensationController {
   private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);
   
   @Autowired
   private CompensationService compensationService;
   
   @Autowired
   private Environment environment;
   
   
   @PostMapping("compensation/{id}")
   public Compensation create(@PathVariable String id, @RequestBody CompensationParts compensationParts) {
	   LOG.debug("Received compensation create request for employeeID [{}]", id);
	   
	   return compensationService.create(id, compensationParts.getSalary(), compensationParts.getEffectiveDate(),
			   environment.getProperty("local.server.port"));
	   
   }
   
   @GetMapping("compensation/{id}")
   public Compensation read(@PathVariable String id) {
	   LOG.debug("Received compensation create request for employeeID [{}]", id);
	   
	   return compensationService.read(id, environment.getProperty("local.server.port"));
   }
}
