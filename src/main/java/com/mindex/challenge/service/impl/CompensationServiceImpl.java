package com.mindex.challenge.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.CompensationService;

@Service
public class CompensationServiceImpl implements CompensationService {
	private final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);
	
	@Autowired
	private CompensationRepository compensationRepository; 
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String url = "http://localhost:";
	
	@Override
	public Compensation create(String id, String salary, Date effectiveDate, String port) {
		LOG.debug("Creating compensation with employee id [{}]", id);
		
		Employee employee = restTemplate.getForEntity(url + port + "/employee/{id}",Employee.class, id).getBody();
		Compensation compensation = new Compensation();
		compensation.setEmployee(employee);
		compensation.setSalary(salary);
		compensation.setEffectiveDate(effectiveDate);
		
		return compensationRepository.insert(compensation);
	}

	@Override
	public Compensation read(String id, String port) {
		LOG.debug("Creating compensation with employee id [{}]", id);
		
		Employee employee = restTemplate.getForEntity(url + port + "/employee/{id}", Employee.class, id).getBody();
		return compensationRepository.findByEmployee(employee);
	}

}
